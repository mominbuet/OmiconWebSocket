/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.omicon_ws;

import db.Users;
import db.dbQuery;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author user
 */
@ServerEndpoint("/omicon_endpoint")
public class app_connect {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private static Map<String, Session> peerMap = new TreeMap<String, Session>();
    static Session server = null;
    private static Map<String, Boolean> is_runn = new TreeMap<String, Boolean>();

    private static void sendmsg(String obj, String user, Users user_fromdb) throws IOException {
        try {
            peerMap.get(user).getBasicRemote().sendText(obj);

            System.out.println("sending app link to download " + user + " json " + obj);

            //Users user_fromdb = dbQuery.get_user_by_name(user);
            if (user_fromdb != null) {
                user_fromdb.setExecuted(1);
                //user_fromdb.setCommand("");
                dbQuery.insert_users(user_fromdb);
                dbQuery.insert_log(user, "Executed command for " + user + " command " + obj);
                Logger.getLogger(app_connect.class.getName()).log(Level.WARNING, "sendmsg(String obj, String user) Executed command for " + user + " command " + obj);
            } else {
                dbQuery.insert_log(user, "Null found for user " + user + " on database when execution ended");
                Logger.getLogger(app_connect.class.getName()).log(Level.SEVERE, "sendmsg(String obj, String user) Executed command for " + user + " command " + obj);
            }

        } catch (Exception ex) {
            System.out.println("Exception in sending " + ex.getMessage());
            server.getBasicRemote().sendText(user + " cannot be connected, removing from the list.");
            peerMap.remove(user);
            dbQuery.insert_log(user, "Error sending msg for user " + user);
        }
        try {
            server.getBasicRemote().sendText("sending command to " + user + " json "
                    + obj);
        } catch (Exception ex) {
            System.out.println("Exception in sending in server" + ex.getMessage());
        }
    }

    private static void sendmsg(JSONObject obj, String user) throws IOException {
        try {
            peerMap.get(user).getBasicRemote().sendText(obj.toJSONString());
            if (!obj.containsKey("ack")) {
                System.out.println("sending app link to download " + user + " json "
                        + obj.toJSONString());

                server.getBasicRemote().sendText("sending command to " + user + " json "
                        + obj.toString());
                Logger.getLogger(app_connect.class.getName()).log(Level.WARNING, "sendmsg(JSONObject obj, String user) successfully Executed command for " + user + " command " + obj.toJSONString());
            }
        } catch (Exception ex) {
            System.out.println("Exception in sending " + ex.getMessage());
            server.getBasicRemote().sendText(user + " cannot be connected, removing from the list.");
            peerMap.remove(user);
            Logger.getLogger(app_connect.class.getName()).log(Level.SEVERE, "sendmsg(JSONObject obj, String user) Executed command for " + user + " command " + obj.toJSONString());
        }
    }

    @OnMessage
    public void onMessage(String message, Session peer) {
        System.out.println("incoming from " + peer.getId() + " message " + message);
        String cmd = "", text = "", user = "";
        JSONParser parser = new JSONParser();
        //System.out.println("cmd " + cmd);
        try {
            JSONObject obj = (JSONObject) parser.parse(message);
            //JSONArray array = (JSONArray) obj;
            cmd = (String) obj.get("command");
            text = (String) obj.get("text");
            user = (String) obj.get("user");

        } catch (Exception ex) {
            System.out.println("Exception in parsing " + ex.getMessage());

        }

//        String[] commands = message.split("@@");
//        message = commands[0];
        try {
            JSONObject obj = new JSONObject();
            switch (cmd) {
                case "userList":
                    //peer.getBasicRemote().sendText(message);
                    peer.getBasicRemote().sendText("sending userlist");
                    server = peer;
                    StringBuilder peers_str = new StringBuilder();
                    System.out.println("Keyset " + peerMap.keySet().size());
                    for (String usr : peerMap.keySet()) {
                        //if(peerMap.get(user).getId()!=peer.getId())
                        //peerMap.get(usr).getBasicRemote().sendText(obj.toJSONString());
                        peers_str.append(usr + "/" + peerMap.get(usr).getId() + ",");
                        System.out.println(usr + "/" + peerMap.get(usr).getId() + ",");
                        //peer.getBasicRemote().sendText(user + "/" + peerMap.get(user).getId() + ",");
                    }
                    String res = peers_str.substring(0, peers_str.length() - 1);
                    server.getBasicRemote().sendText(res);
                    break;

                case "dbupload":
                    obj.put("dbupload", text);
                    sendmsg(obj, user);
                    break;
                case "sqlrestore":
                    break;
                case "appInstall":
                    obj.put("appLink", text);
                    sendmsg(obj, user);
                    break;
                case "processlist":
                    obj.put("processlist", text);
                    sendmsg(obj, user);
                    break;
                case "sendprefs":
                    obj.put("sendprefs", text);
                    sendmsg(obj, user);
                    break;
                case "sendlog":
                    obj.put("sendlog", text);
                    sendmsg(obj, user);
                    break;
                case "sqlquery":
                    obj.put("sqlquery", text);
                    sendmsg(obj, user);
                    break;
                case "sendLocs":
                    obj.put("sendLocs", text);
                    sendmsg(obj, user);
                    break;
                case "changePref":
                    obj.put("changePref", text);
                    sendmsg(obj, user);
                    break;
                case "killprocess":
                    obj.put("killprocess", text);
                    sendmsg(obj, user);
                    break;
                case "queryresult":
                    server.getBasicRemote().sendText("Got query resut from " + user);
                    server.getBasicRemote().sendText("Query resut " + text);
                    //Util.write_file(user + "\r\n" + text);
                    break;
                case "ack":
                    obj.put("ack", text);
                    sendmsg(obj, user);
                    break;
                case "addNotification":

                    obj.put("notification", text);
//                    if (user.equals("all")) {
//                        for (String usr : peerMap.keySet()) {
//                            peerMap.get(usr).getBasicRemote().sendText(obj.toJSONString());
//                        }
//                    } else {
                    sendmsg(obj, user);
                    break;
                case "user_email":
                    //peerMap.put(message, peer);
                    break;
                case "received_app":
                    server.getBasicRemote().sendText("<b>Sent from android(" + user + ") " + text + "</b>");
                    break;

                case "appcopnnect":
                    if (user != " ") {
                        if (peerMap.get(user) != null) {
                            peerMap.remove(message);
                        }
                        peerMap.put(user, peer);
                        System.out.println("user " + user + " peer " + peer.getId());
                        Users dbuser = Util.insert_user(user, peer);
                        //System.out.println("Command for "+dbuser.getUser()+    ((dbuser.getCommand()!=null)?dbuser.getCommand():" none"));
                        if (dbuser.getCommand() != null && dbuser.getExecuted() == 0) {

                            //is_runn.put(user, Boolean.TRUE);
                            //obj.put("dbupload", text);
                            //obj.put("sqlquery", "update trn_expense set entry_state = 1, is_uploaded=0 where offline_exp_no in  (1267,1274,1285,1386) and user_no = 220; update trn_expense_det set entry_state = 1, is_uploaded=0 where offline_exp_no in  (1267,1274,1285,1386);");
                            //sendmsg(obj, user);
                            //obj.put("changePref", "isProcessing,0");
                            Logger.getLogger(app_connect.class.getName()).log(Level.WARNING, "sending command for " + user + " command " + dbuser.getCommand());
                            sendmsg(dbuser.getCommand(), user, dbuser);

                        }
                        //System.out.println("putting " + user + " sess " + peer.getId());
                    }
                    break;
                default:
                    if (user.isEmpty()) {
                        if (peerMap.get(user) != null) {
                            peerMap.remove(message);
                        }
                        peerMap.put(user, peer);
                    }
                    break;
            }

        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session peer) {
        //System.out.println(peer.getId() + " has opened a connection");

        peers.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        try {
            server.getBasicRemote().sendText(peer.getId() + " closed its connection");
            peers.remove(peer);
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
