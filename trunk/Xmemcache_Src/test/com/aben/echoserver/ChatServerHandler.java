package com.aben.echoserver;

import  java.util.List;
import  java.util.concurrent.CopyOnWriteArrayList;
import  java.util.concurrent.atomic.AtomicInteger;

import  com.google.code.yanf4j.core.Session;
import  com.google.code.yanf4j.core.impl.HandlerAdapter;

public   class  ChatServerHandler  extends  HandlerAdapter  {
    List <Session>  sessionList  =   new  CopyOnWriteArrayList <Session> ();
    AtomicInteger userId  =   new  AtomicInteger();
    String userName = "userName";

    @Override
     public  void  onMessageReceived(Session session, Object t) {
         if  (t.equals( " quit " ))
            session.close();
         else 
            broadcast(session,  " [ "  +  session.getAttribute(userName) +  " ] say: "  +  t);

    } 

    @Override
     public   void  onSessionClosed(Session session) {
        sessionList.remove(session);
        broadcast(session, session.getAttribute(userName)  +   "  leave room " );
    }

     private   void  broadcast(Session s, String msg) {
         for  (Session session :  this .sessionList) {
            session.write(msg);
        }
    }

    @Override
     public   void  onSessionCreated(Session session) {
        sessionList.add(session);
         //  给session取名 
        session.setAttributeIfAbsent(userName,generateUserName());
         //  广播某人进来 
        broadcast(session, session.getAttribute(userName)  +   "  enter room " );
    }

     private  String generateUserName() {
         return   " user "   +  userId.incrementAndGet();
    }

}
