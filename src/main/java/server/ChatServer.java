package server;

import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chatserver")
public class ChatServer {
	
	private static ArrayList<Session> sessions = new ArrayList<>();
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("open connection");
		sessions.add(session);
	}
	
	@OnMessage
	public void onMessage(Session session, String message) {
		for (Session s : sessions) {
			if (s != session) {
				sendMessage(s, message);
			}
		}
	}
	
	@OnClose
	public void onClose(Session session) {
		System.out.println("close....");
		sessions.remove(session);
	}
	
	private void sendMessage(Session session, String message) {
		if (session.isOpen()) {
			Async remote = session.getAsyncRemote();
			remote.sendText(message);
		}
	}
}
