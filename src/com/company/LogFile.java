package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {
    File f;
    Document dom;
    Element e = null;
    Node temp = null;
    DocumentBuilderFactory dbf;
    DocumentBuilder db;

    private static Node createPlayer(Document doc, Player gPlayer)
    {
        Element player = doc.createElement("Player");
        player.setAttribute("id", Integer.toString(gPlayer.getId()));
        player.setAttribute("name", gPlayer.getName());
        player.setAttribute("symbol", gPlayer.getMark());
        return player;
    }

    private static Node createStep(Document doc, int pos, int turn)
    {
        Element step = doc.createElement("Step");
        step.setAttribute("num", Integer.toString(turn));
        step.setAttribute("playerId", Integer.toString(((turn + 1) % 2 + 1)));
        step.setTextContent(Integer.toString(pos));
        return step;
    }

    public LogFile(Player p1, Player p2)
    {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        File dir = new File(System.getProperty("user.dir") + "\\Logs\\");
        if (!dir.exists())
            dir.mkdir();
        f = new File(dir.getPath() + "\\" + formatForDateNow.format(new Date()) + "_" + p1.getName() + "_vs_" + p2.getName() + ".xml");
        if (!f.exists())
            try
            {
                f.createNewFile();
            }
            catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        dbf = DocumentBuilderFactory.newInstance();
        try
        {
            db = dbf.newDocumentBuilder();
            dom = db.newDocument();
            e = dom.createElement("Gameplay");
            dom.appendChild(e);
            e.appendChild(createPlayer(dom, p1));
            e.appendChild(createPlayer(dom, p2));
            temp = e.appendChild(dom.createElement("Game"));
        }
        catch (ParserConfigurationException pce)
        {
            System.out.println("XML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public void addStep(int pos, int turn)
    {
       temp.appendChild(createStep(dom, pos, turn));
    }

    public void endLog()
    {
        temp = e.appendChild(dom.createElement("GameResult"));
        Player p0 = new Player();
        p0.setMark("-");
        p0.setId(0);
        p0.setName("");
        temp.appendChild(createPlayer(dom, p0));
        try
        {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(f)));
        }
        catch (TransformerException te)
        {
            System.out.println(te.getMessage());
        }
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }

    public void endLog(Player p)
    {
        temp = e.appendChild(dom.createElement("GameResult"));
        temp.appendChild(createPlayer(dom, p));
        try
        {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(f)));
        }
        catch (TransformerException te)
        {
            System.out.println(te.getMessage());
        }
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
