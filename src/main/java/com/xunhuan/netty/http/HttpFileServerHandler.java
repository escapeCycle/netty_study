package com.xunhuan.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);

            return;
        }

        if (request.method() != HttpMethod.GET) {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        final String uri = request.uri();
        final String path = sanitizeUri(uri);
        if(path == null){
            sendError(ctx,HttpResponseStatus.FORBIDDEN);
            return;
        }
        File file = new File(path);
        if(file.isHidden() || !file.exists()){
            sendError(ctx,HttpResponseStatus.NOT_FOUND);
            return;
        }
        if(file.isDirectory()){
            if(uri.endsWith("/")){
                sendListing(ctx,file);
            }else{
                sendRedirect(ctx,uri+"/");
                return;
            }
        }
        if (!file.isFile()){
            sendError(ctx,HttpResponseStatus.FORBIDDEN);
            return;
        }
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file,"r");
        }catch (FileNotFoundException e){
            sendError(ctx,HttpResponseStatus.NOT_FOUND);
            return;
        }

        long length = randomAccessFile.length();
        HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpUtil.setContentLength(httpResponse,length);
        setContentTypeHeader(httpResponse,file);

        if(HttpUtil.isKeepAlive(httpResponse)){
            httpResponse.headers().set("connection",HttpHeaderNames.CONNECTION);
        }
        ctx.write(httpResponse);
        ChannelFuture sendFileFuture;
        sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile,0,length,8192),ctx.newProgressivePromise());

        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {

                if(total < 0){
                    System.out.println("Transfer progress : " + progress);
                }else {
                    System.out.println("Transfer progress : " + progress + "/" + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("Transfer omplete");
            }
        });
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if(!HttpUtil.isKeepAlive(request)){
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        if(ctx.channel().isActive()){
            sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    private String sanitizeUri(String uri) {

        try {
            URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException ee) {
                throw new Error();
            }

        }
//        if (!uri.endsWith(url)) {
//            return null;
//        }
        if (!uri.startsWith("/")) {
            return null;
        }
        uri = uri.replace('/', File.separatorChar);

        if (uri.contains(File.separator + '.')
                || uri.contains('.' + File.separator)
                || uri.startsWith(".")
                || uri.endsWith(".")
                || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }
        return System.getProperty("user.dir") + File.separator + uri;

    }

    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    private static void sendListing(ChannelHandlerContext ctx, File dir) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        StringBuilder builder = new StringBuilder();
        String path = dir.getPath();
        builder.append("<!DOCTYPE html>\r\n");
        builder.append("<html><head><title>");
        builder.append(path);
        builder.append(" 目录 ： ");
        builder.append("</title></head><body>\r\n");
        builder.append("<h3>");
        builder.append(dir).append(" 目录: ");
        builder.append("</h3>\r\n");
        builder.append("<ul>");
        builder.append("<li>链接: <a href=\"../\">..</a></li>\r\n");
        for (File file : dir.listFiles()) {
            if (file.isHidden() || !file.canRead()) {
                continue;
            }
            String fileName = file.getName();
            if (!ALLOWED_FILE_NAME.matcher(fileName).matches()) {
                continue;
            }
            builder.append("<li>链接: <a href=\"");
            builder.append(fileName);
            builder.append("\">");
            builder.append(fileName);
            builder.append("</a></li>\r\n");

        }

        builder.append("</ul></body><html>\r\n");
        ByteBuf buffer = Unpooled.copiedBuffer(builder, CharsetUtil.UTF_8);
        response.content().writeBytes(buffer);
        buffer.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaderNames.LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mime = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mime.getContentType(file.getPath()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("active");
        super.channelActive(ctx);
    }
}
