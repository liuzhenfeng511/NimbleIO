package com.gifisan.nio.extend.plugin.http;

import com.gifisan.nio.component.Session;
import com.gifisan.nio.component.protocol.future.ReadFuture;

public interface HttpSession {

	public abstract void active(Session ioSession);

	public abstract void flush(ReadFuture future);

	public abstract long getCreateTime();

	public abstract Session getIOSession();

	public abstract long getLastAccessTime();

	public abstract String getSessionID();
	
	public abstract boolean isValidate();
	
	public abstract HttpContext getContext();

}