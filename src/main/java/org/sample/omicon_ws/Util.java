/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.omicon_ws;

import db.Users;
import db.dbQuery;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author user
 */
public class Util {

    public static Users insert_user(String user_name, Session peer) {
        Users user = new Users();
        user.setIsAlive(1);
        user.setSession(peer.getId());
        user.setUser(user_name.trim());
        dbQuery.insert_users(user);
        return dbQuery.get_user_by_name(user_name);
    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Integer>> list
                = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                    Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // Convert sorted map back to a Map
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static void write_file(String txt) {
        File outf = new File("output.txt");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(outf);
        } catch (FileNotFoundException ex) {

            System.out.println("Output file printwriter error! Please check if file paths are correct.");

        }

        String tab = "\t";
        String LF = "\r\n";

        StringBuilder sb = new StringBuilder();
        sb.append(txt);
        sb.append(LF);

        pw.println(sb.toString());
        pw.flush();

        pw.checkError();
        pw.close();
    }
}
