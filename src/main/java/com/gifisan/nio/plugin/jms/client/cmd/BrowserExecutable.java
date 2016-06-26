package com.gifisan.nio.plugin.jms.client.cmd;

import java.util.HashMap;

import com.gifisan.nio.common.Logger;
import com.gifisan.nio.common.LoggerFactory;
import com.gifisan.nio.common.StringUtil;
import com.gifisan.nio.common.cmd.CmdResponse;
import com.gifisan.nio.common.cmd.CommandContext;
import com.gifisan.nio.plugin.jms.Message;
import com.gifisan.nio.plugin.jms.client.MessageBrowser;

public class BrowserExecutable extends MQCommandExecutor {

	private Logger	logger	= LoggerFactory.getLogger(BrowserExecutable.class);

	public CmdResponse exec(CommandContext context, HashMap<String, String> params) {

		CmdResponse response = new CmdResponse();

		MessageBrowser browser = getMessageBrowser(context);

		if (browser == null) {
			response.setResponse("请先登录！");
			return response;
		}
		String messageID = params.get("-mid");
		if (StringUtil.isNullOrBlank(messageID)) {
			response.setResponse("参数不正确！\n" + "example:\n" + "browser -mid:mid");
			return response;
		}
		try {
			Message message = browser.browser(messageID);
			if (message == null) {
				response.setResponse("没有此ID的消息！");
			} else {
				response.setResponse(message.toString());
			}
		} catch (Exception e) {
			logger.debug(e);
		}

		return response;
	}
}
