package com.gifisan.nio.acceptor;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.gifisan.nio.common.CloseUtil;
import com.gifisan.nio.common.Logger;
import com.gifisan.nio.common.LoggerFactory;
import com.gifisan.nio.component.TCPEndPoint;
import com.gifisan.nio.component.protocol.ProtocolDecoder;
import com.gifisan.nio.component.protocol.future.IOReadFuture;
import com.gifisan.nio.component.protocol.nio.NIOProtocolDecoder;

public class ServerProtocolDecoder extends NIOProtocolDecoder implements ProtocolDecoder {

	private Logger			logger	= LoggerFactory.getLogger(ServerProtocolDecoder.class);
	private StringBuilder	http		= new StringBuilder();
	private byte[]		httpArray	;

	public ServerProtocolDecoder() {

		http.append("HTTP/1.1 200 OK\n");
		http.append("Server: nimbleio/1.1\n");
		http.append("Content-Type:text/html;charset=GBK\n\n");
		
		http.append("<!DOCTYPE html>\n");
		http.append("<html lang=\"en\">\n");
		http.append("	<head>\n");
		http.append("		<meta name =\"viewport\" content =\"initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no\">\n");
		http.append("		<title>nimbleio</title>\n");
		http.append("	</head>\n");
		http.append("<body>\n");
		http.append("	<p style=\"color:#FDA58C;\">\n");
		http.append("		im not an http server :)  ");
		http.append("		<a style=\"color:#F94F4F;\" href=\"https://github.com/NimbleIO/NimbleIO#readme\" >fork me@https://github.com/nimbleio/nimbleio</a>\n");
		http.append("	</p>\n");
		http.append("</body>\n");
		http.append("</html>");

		httpArray = http.toString().getBytes();
	}

	public IOReadFuture doDecodeExtend(TCPEndPoint endPoint, ByteBuffer header, byte type) throws IOException {

		// HTTP REQUEST ?
		if (type == ProtocolDecoder.TYPE_HTTP) {
			ByteBuffer buffer = ByteBuffer.wrap(httpArray);
			endPoint.write(buffer);
//			endPoint.endConnect();
			logger.debug("来自[ {}:{} ]的HTTP请求", endPoint.getRemoteAddr(),endPoint.getRemotePort());
			return null;
		}else{
			CloseUtil.close(endPoint);
			return null;
		}
	}



}
