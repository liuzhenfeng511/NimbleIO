package com.gifisan.nio.component;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.gifisan.nio.Attachment;

public abstract class AbstractEndPoint implements EndPoint {

	private Attachment				attachment;
	private NIOContext				context;
	private Integer				endPointID;
	protected InetSocketAddress		local;
	protected InetSocketAddress		remote;

	public AbstractEndPoint(NIOContext context) {
		this.context = context;
		this.endPointID = context.getSequence().AUTO_ENDPOINT_ID.getAndIncrement();
	}

	public void attach(Attachment attachment) {
		this.attachment = attachment;
	}

	public Attachment attachment() {
		return attachment;
	}

	public String getLocalHost() {
		return local.getHostName();
	}

	public int getLocalPort() {
		return local.getPort();
	}

	public String getLocalAddr() {

		InetAddress address = local.getAddress();

		if (address == null) {
			return "127.0.0.1";
		}

		return address.getHostAddress();
	}

	public abstract InetSocketAddress getLocalSocketAddress();

	public String getRemoteAddr() {

		InetSocketAddress address = getRemoteSocketAddress();

		if (address == null) {

			return "closed";
		}

		return address.getAddress().getHostAddress();
	}

	public String getRemoteHost() {

		InetSocketAddress address = getRemoteSocketAddress();

		if (address == null) {

			return "closed";
		}

		return address.getAddress().getHostName();
	}

	public int getRemotePort() {

		InetSocketAddress address = getRemoteSocketAddress();

		if (address == null) {

			return -1;
		}

		return address.getPort();
	}

	public NIOContext getContext() {
		return context;
	}

	public String toString() {
		return new StringBuilder("[").append(getMarkPrefix()).append("(id:").append(endPointID).append(") remote /")
				.append(this.getRemoteHost()).append("(").append(this.getRemoteAddr()).append("):")
				.append(this.getRemotePort()).append("]").toString();
	}

	protected abstract String getMarkPrefix();

	public Integer getEndPointID() {
		return endPointID;
	}
}
