
+import java.io.*;
import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;


public class Midlet extends MIDlet implements CommandListener, DiscoveryListener {

    List searchdevice,dev_list,choosemode,appmode,runmode,moption,pwroption,fireoption,ioption,vlcoption,keyoption,selectkeys,selectkeys1,selectkeys2,selectkeys3,switchapp;
    Command exit, back,send,help,ok,cancel;
    canvas can;
    Alert search;
    LiveDesktop live;
    TextBox cmd,type;
    Form form1,form2,form3,form4;
    Display display;
    java.util.Vector devices, services;
    LocalDevice local;     ...................../*local Bluetooth Manager (First the application retrieves a reference to the
												Bluetooth Manager from the LocalDevice. Client applications retrieve a reference 
												to the DiscoveryAgent, which provides all the discovery */
    DiscoveryAgent agent;  .....................//discovery agent
    DataOutputStream dout;
    DataInputStream din;
    StreamConnection conn = null;
    int currentDevice = 0,i;
    static int count=0,count1=0;
    
    String key1="";
    Alert a=null;
    String movekey[]={"Tab","Back","Down","Up","Right","Left","Ok","Select All","App-Menubar","FileOption","Escape"}; //11 Elements
    String firefox[]={"Run Browser","Home","New Tab","Close Tab","Next Tab","Refresh","Back","Add Bookmark","Zoom Out","Zoom In","Scroll Down","Scroll Up"}; //12 Elements
    String key[]={"Start","Selection Keys","Switch Application","Close"};  // 4 Elements
    String media[]={"Run Player","Open Files","Selection keys","Play/Pause","Previous","Next","Volume Up","Volume Down","Forward","Mute","Exit"}; // 11 Elements
    String power[]={"Run PowerPoint","Open File","Selection Keys","Exit Slideshow","Next Slide","Previous Slide","Exit"}; //7 Elements
//used as an indicator to the device queried for
//the echo server

    public void startApp() {
        searchdevice = new List("Device Search", Choice.IMPLICIT); //the main menu came from 'login' class
        dev_list = new List("Select Device", Choice.IMPLICIT); //the list of devices
        choosemode = new List("Choose Operation ",Choice.IMPLICIT);
        keyoption = new List("Keyboard Option",Choice.IMPLICIT);
        appmode = new List("Application Selection", Choice.IMPLICIT);
        fireoption = new List("Firefox Operation",Choice.IMPLICIT);
        ioption=new List("Internet Explorer Option",Choice.IMPLICIT);
        moption = new List("WMedia Operation",Choice.IMPLICIT);
        vlcoption = new List("VLC Player Operation",Choice.IMPLICIT);
        pwroption = new List("PowerPoint Operation",Choice.IMPLICIT);
        selectkeys = new List("Selection Keys",Choice.IMPLICIT);
        selectkeys1 = new List("Selection Keys",Choice.IMPLICIT);
        selectkeys2 = new List("Selection Keys",Choice.IMPLICIT);
        selectkeys3 = new List("Selection Keys",Choice.IMPLICIT);
        switchapp = new List("Switch-App Option",Choice.IMPLICIT);
        runmode = new List("Run Command Mode",Choice.IMPLICIT);
        form1=new Form("Help");
        search = new Alert("Confirmation","Do you want to exit ?",null,null);
        search.setTimeout(60000);  
        
        form1.append("First press \"Switch\" to enable switching view. Use \"Select\" item to choose appropriate Application. Finally for confirmation and  to close switching view press \"Ok\".");    
        cmd = new TextBox("Run Command","",140,TextField.ANY);
        type = new TextBox("URL or Serach Keys","",200,TextField.ANY);
        exit = new Command("Exit", Command.EXIT, 1);
        back = new Command("Back", Command.BACK, 1);
        send = new Command("Send", Command.OK, 1);
        help = new Command("Help", Command.HELP, 1);
        ok = new Command("Ok",Command.OK,1);
        cancel = new Command("Cancel",Command.CANCEL,1);
        display = Display.getDisplay(this);

        searchdevice.addCommand(exit);
        search.addCommand(ok);
        search.addCommand(cancel);
        cmd.addCommand(send);
        cmd.addCommand(back);
        type.addCommand(send);
        type.addCommand(back);
        dev_list.addCommand(exit);
        choosemode.addCommand(exit);
        appmode.addCommand(exit);
        keyoption.addCommand(back);
        appmode.addCommand(back);
        runmode.addCommand(back);
        
        moption.addCommand(back);
        vlcoption.addCommand(back);
        pwroption.addCommand(back);
        fireoption.addCommand(back);
        ioption.addCommand(back);
        selectkeys.addCommand(back);
        selectkeys1.addCommand(back);
        selectkeys2.addCommand(back);
        selectkeys3.addCommand(back);
        form1.addCommand(back);
        switchapp.addCommand(back);
        switchapp.addCommand(help);
        
        searchdevice.setCommandListener(this);
        search.setCommandListener(this);
        dev_list.setCommandListener(this);
        choosemode.setCommandListener(this);
        keyoption.setCommandListener(this);
        cmd.setCommandListener(this);
        type.setCommandListener(this);
        appmode.setCommandListener(this);
        moption.setCommandListener(this);
        vlcoption.setCommandListener(this);
        pwroption.setCommandListener(this);
        fireoption.setCommandListener(this);
        ioption.setCommandListener(this);
        runmode.setCommandListener(this);
        selectkeys.setCommandListener(this);
        selectkeys1.setCommandListener(this);
        selectkeys2.setCommandListener(this);
        selectkeys3.setCommandListener(this);
        switchapp.setCommandListener(this);
        
        



        searchdevice.append("Search Device", null);
        choosemode.append("Mouse Mode",null);
        choosemode.append("Live Desktop",null);
        choosemode.append("Keyboard Mode",null);
        choosemode.append("Application Mode",null);
        choosemode.append("Utilities",null);
        appmode.append("Mozila Firefox", null);
        appmode.append("Internet Explorer", null);
        appmode.append("Window Media Player", null);
        appmode.append("VLC Player", null);
        appmode.append("PowerPoint", null);
        runmode.append("Run Commands",null);
        runmode.append("Shutdown PC",null);
        runmode.append("Restart PC", null);
        runmode.append("Logoff user",null);
        switchapp.append("Switch",null);
        switchapp.append("Select",null);
        switchapp.append("Ok",null);
        
        for(i=0;i<11;i++)
        {
            selectkeys.append(movekey[i],null);
            selectkeys1.append(movekey[i],null);
            selectkeys2.append(movekey[i],null);
            selectkeys3.append(movekey[i],null);
        }
        for(i=0;i<4;i++)
        {
        keyoption.append(key[i],null);
        }
        for(i=0;i<11;i++)

        {
        moption.append(media[i],null);
        }
        for(i=0;i<7;i++)
        {
        pwroption.append(power[i],null);
        }
        for(i=0;i<12;i++)
        {
         fireoption.append(firefox[i],null);
         ioption.append(firefox[i],null);
        }
        
        for(i=0;i<9;i++)
        {
        vlcoption.append(media[i],null);
        }
        vlcoption.append("Rewind",null);
        vlcoption.append("Mute",null);
        vlcoption.append("Aspect Ratio",null);
        vlcoption.append("Crop Screen",null);
        vlcoption.append("Fullscreen",null);
        vlcoption.append("Exit",null);
        LogIn li=new LogIn(this,searchdevice);
      // display.setCurrent(searchdevice);


    }

    public void commandAction(Command com, Displayable dis) {
        if (com == exit) { //exit triggered from the main form
            display.setCurrent(search);
        }
        if(com == back)
        {     
            if(dis==runmode || dis==appmode || dis==keyoption)
            display.setCurrent(choosemode);
            else if(dis==moption || dis==pwroption || dis==fireoption || dis==ioption || dis==vlcoption)
             display.setCurrent(appmode);
            else if(dis==cmd)
                display.setCurrent(runmode);
            else if(dis==selectkeys)
                display.setCurrent(keyoption);
            else if(dis==selectkeys1)
                display.setCurrent(moption);
            else if(dis==selectkeys2)
                display.setCurrent(vlcoption);
            else if(dis==selectkeys3)
                display.setCurrent(pwroption);
            else if(dis==switchapp)
                display.setCurrent(keyoption);
            else if(dis==form1)
                display.setCurrent(switchapp);
        }
        
        if(com==help)
        {
            display.setCurrent(form1);
        }

        if(com == send)
        {
            try
            {
                key1=cmd.getString()+'\n';
                key1="run "+key1;+
                dout.writeChars(key1);  //Write in output stram of "dout" Object.
                dout.flush();           //Flush the Pipe. 
                cmd.setString("");
                Alert a=new Alert("Success","Command sent successfuly.",null,AlertType.INFO);
                a.setTimeout(700);
                display.setCurrent(a);
            }
            catch(Exception e)
            {
                this.do_alert("Error sending data", 4000);
            }
        }
        
        if(com == ok)
            {
            destroyApp(false);
            notifyDestroyed();
            }
        
        if(com == cancel)
            display.setCurrent(choosemode);
     
        if (com == List.SELECT_COMMAND) {
            
            if (dis == searchdevice) { //select triggered from the main from
                if (searchdevice.getSelectedIndex() >= 0) { //find devices
                     FindDevices();
                     searchdevice.delete(0);
                     Alert search1 =new Alert("Bluetooth","Searching for Bluetooth devices...",null,null);
                      search1.setTimeout(60000);
                      search1.setCommandListener(this);
                     display.setCurrent(search1);
                }
            }
            
            
            if (dis == dev_list) { //select triggered from the device list
                int index = dev_list.getSelectedIndex();   // new
                this.FindServices((RemoteDevice) devices.elementAt(index));  //new
                 this.do_alert("Connecting.....",60000);
                
            }
            
            
            if (dis == choosemode) { 
               if (choosemode.getSelectedIndex() == 0) 
               {
                   if(count==0)
                   {
                   can=new canvas(conn,display,dout,din,choosemode);
                   display.setCurrent(can);
                   can.show(can);
                   count=1;
               }
               else 
               {
                   can.show(can);
                   can.draw();
               }
                   
               } 
               
               else if (choosemode.getSelectedIndex() == 1)  // Live Desktop
                   {
                   if(count1==0)
                   {
                   live = new LiveDesktop(conn,display,dout,din,choosemode);
                   live.show(live);
                   display.setCurrent(live);
                    count1=1;
                    
               }
               else 
               {
                   live.show(live);
    Fg              live.draw();
               }
                   
               }
                    
              else if (choosemode.getSelectedIndex() == 2)  // Keyboard Option
                    display.setCurrent(keyoption);
               else if (choosemode.getSelectedIndex() == 3)  //find devices
                    display.setCurrent(appmode);
               else if (choosemode.getSelectedIndex() == 4) //find devices
                    display.setCurrent(runmode);
                }
            
            
            if(dis==keyoption)
            {
               try{
                  
                    if(keyoption.getSelectedIndex()==0)
                        dout.writeChars("keyboard start"+'\n');
                   else if(keyoption.getSelectedIndex()==1)
                        display.setCurrent(selectkeys);
                   else if(keyoption.getSelectedIndex()==2)
                        display.setCurrent(switchapp);
                     else  if(keyoption.getSelectedIndex()==3)
                        dout.writeChars("keyboard close"+'\n');
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
            
            if(dis==switchapp)
            {
               try{
                  
                    if(switchapp.getSelectedIndex()==0)
                        dout.writeChars("switch view"+'\n');
                   else if(switchapp.getSelectedIndex()==1)
                        dout.writeChars("switch select"+'\n');
                     else  if(switchapp.getSelectedIndex()==2)
                        dout.writeChars("switch ok"+'\n');
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
            
            
            if(dis == selectkeys)
            {
               try{
                  
                    if(selectkeys.getSelectedIndex()==0)
                        dout.writeChars("movekey tab"+'\n');
                    else if(selectkeys.getSelectedIndex()==1)
                        dout.writeChars("movekey back"+'\n');
                   else if(selectkeys.getSelectedIndex()==2)
                        dout.writeChars("movekey down"+'\n');
                   else  if(selectkeys.getSelectedIndex()==3)
                        dout.writeChars("movekey up"+'\n');
                    else  if(selectkeys.getSelectedIndex()==4)
                        dout.writeChars("movekey right"+'\n');
                    else  if(selectkeys.getSelectedIndex()==5)
                        dout.writeChars("movekey left"+'\n');
                    else  if(selectkeys.getSelectedIndex()==6)
                        dout.writeChars("movekey ok"+'\n');
                    else  if(selectkeys.getSelectedIndex()==7)
                        dout.writeChars("movekey sall"+'\n');
                     else  if(selectkeys.getSelectedIndex()==8)
                        dout.writeChars("movekey menubar"+'\n');
                     else  if(selectkeys.getSelectedIndex()==9)
                        dout.writeChars("movekey foption"+'\n');
                    else  if(selectkeys.getSelectedIndex()==10)
                        dout.writeChars("movekey escape"+'\n');
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
           
            
          
            if (dis == appmode) {
                 
                if(appmode.getSelectedIndex()== 0) {
                display.setCurrent(fireoption);
                }
                else if(appmode.getSelectedIndex()==1) {
                display.setCurrent(ioption);
                }
                else if(appmode.getSelectedIndex()==2) {
                  display.setCurrent(moption);
                }
                else if(appmode.getSelectedIndex()==3) {
                display.setCurrent(vlcoption);
                }
                else if(appmode.getSelectedIndex()==4) {
                display.setCurrent(pwroption);
                }
              }
            
            
        
            if(dis==fireoption)
            {
               try{
                  
                    if(fireoption.getSelectedIndex()==0)
                        dout.writeChars("mozila firefox.exe"+'\n');
                   else if(fireoption.getSelectedIndex()==1)
                        dout.writeChars("mozila home"+'\n');
                   else if(fireoption.getSelectedIndex()==2)
                        dout.writeChars("mozila newt"+'\n');
                   else  if(fireoption.getSelectedIndex()==3)
                        dout.writeChars("mozila closet"+'\n');
                    else  if(fireoption.getSelectedIndex()==4)
                        dout.writeChars("mozila nextt"+'\n');
                    else  if(fireoption.getSelectedIndex()==5)
                        dout.writeChars("mozila refresh"+'\n');
                    else  if(fireoption.getSelectedIndex()==6)
                        dout.writeChars("mozila back"+'\n');
                    else  if(fireoption.getSelectedIndex()==7)
                        dout.writeChars("mozila addbook"+'\n');
                     else  if(fireoption.getSelectedIndex()==8)
                        dout.writeChars("mozila zoomout"+'\n');
                     else  if(fireoption.getSelectedIndex()==9)
                        dout.writeChars("mozila zoomin"+'\n');
                    else  if(fireoption.getSelectedIndex()==10)
                        dout.writeChars("mozila scrolldown"+'\n');
                    else  if(fireoption.getSelectedIndex()==11)
                        dout.writeChars("mozila scrollup"+'\n');
                    
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
            
            
            if(dis==ioption)
            {
               try{
                  
                   if(ioption.getSelectedIndex()==0)
                        dout.writeChars("internet iexplore.exe"+'\n');
                   else if(ioption.getSelectedIndex()==1)
                        dout.writeChars("internet home"+'\n');
                   else if(ioption.getSelectedIndex()==2)
                        dout.writeChars("internet newt"+'\n');
                   else  if(ioption.getSelectedIndex()==3)
                        dout.writeChars("internet closet"+'\n');
                    else  if(ioption.getSelectedIndex()==4)
                        dout.writeChars("internet nextt"+'\n');
                    else  if(ioption.getSelectedIndex()==5)
                        dout.writeChars("internet refresh"+'\n');
                    else  if(ioption.getSelectedIndex()==6)
                        dout.writeChars("internet back"+'\n');
                    else  if(ioption.getSelectedIndex()==7)
                        dout.writeChars("internet addbook"+'\n');
                     else  if(ioption.getSelectedIndex()==8)
                        dout.writeChars("internet zoomout"+'\n');
                     else  if(ioption.getSelectedIndex()==9)
                        dout.writeChars("internet zoomin"+'\n');
                    else  if(ioption.getSelectedIndex()==10)
                        dout.writeChars("internet scrolldown"+'\n');
                    else  if(ioption.getSelectedIndex()==11)
                        dout.writeChars("internet scrollup"+'\n');
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
          
            
            if(dis==moption)
            {
              try{
                  
                  if(moption.getSelectedIndex()==0)
                        dout.writeChars("media wmplayer.exe"+'\n');
                   else  if(moption.getSelectedIndex()==1)
                        dout.writeChars("media ofile"+'\n');
                    else  if(moption.getSelectedIndex()==2)
                    {
                      display.setCurrent(selectkeys1);
                    }
                    else if(moption.getSelectedIndex()==3)
                        dout.writeChars("media Play/Pause"+'\n');
                   else  if(moption.getSelectedIndex()==4)
                        dout.writeChars("media Previous"+'\n');
                    else  if(moption.getSelectedIndex()==5)
                        dout.writeChars("media Next"+'\n');
                    else  if(moption.getSelectedIndex()==6)
                        dout.writeChars("media Vup"+'\n');
                    else if(moption.getSelectedIndex()==7)
                        dout.writeChars("media Vdown"+'\n');
                    else if(moption.getSelectedIndex()==8)
                        dout.writeChars("media Forward"+'\n');
                   else if(moption.getSelectedIndex()==9)
                        dout.writeChars("media Mute"+'\n');
                    else if(moption.getSelectedIndex()==10)
                        dout.writeChars("media Close"+'\n');
                    dout.flush();
                   
               } 
              catch(Exception e){
                  
              }  
            }
           
             if(dis == selectkeys1)
            {
               try{
                  
                    if(selectkeys1.getSelectedIndex()==0)
                        dout.writeChars("movekey tab"+'\n');
                    else if(selectkeys1.getSelectedIndex()==1)
                        dout.writeChars("movekey back"+'\n');
                   else if(selectkeys1.getSelectedIndex()==2)
                        dout.writeChars("movekey down"+'\n');
                   else  if(selectkeys1.getSelectedIndex()==3)
                        dout.writeChars("movekey up"+'\n');
                    else  if(selectkeys1.getSelectedIndex()==4)
                        dout.writeChars("movekey right"+'\n');
                    else  if(selectkeys1.getSelectedIndex()==5)
                        dout.writeChars("movekey left"+'\n');
                    else  if(selectkeys1.getSelectedIndex()==6)
                        dout.writeChars("movekey ok"+'\n');
                    else  if(selectkeys1.getSelectedIndex()==7)
                        dout.writeChars("movekey sall"+'\n');
                     else  if(selectkeys1.getSelectedIndex()==8)
                        dout.writeChars("movekey menubar"+'\n');
                     else  if(selectkeys1.getSelectedIndex()==9)
                        dout.writeChars("movekey foption"+'\n');
                    else  if(selectkeys1.getSelectedIndex()==10)
                        dout.writeChars("movekey escape"+'\n');
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
             
             
            if(dis==vlcoption)
            {
              try{
                  
                  if(vlcoption.getSelectedIndex()==0)
                        dout.writeChars("vplayer vlc"+'\n');
                   else  if(vlcoption.getSelectedIndex()==1)
                        dout.writeChars("vplayer ofile"+'\n');
                    else  if(vlcoption.getSelectedIndex()==2)
                    {
                      display.setCurrent(selectkeys2);
                    }
                     else  if(vlcoption.getSelectedIndex()==3)
                        dout.writeChars("vplayer Play/Pause"+'\n');
                   else  if(vlcoption.getSelectedIndex()==4)
                        dout.writeChars("vplayer Previous"+'\n');
                    else  if(vlcoption.getSelectedIndex()==5)
                        dout.writeChars("vplayer Next"+'\n');
                    else  if(vlcoption.getSelectedIndex()==6)
                        dout.writeChars("vplayer Vup"+'\n');
                    else if(vlcoption.getSelectedIndex()==7)
                        dout.writeChars("vplayer Vdown"+'\n');
                    else if(vlcoption.getSelectedIndex()==8)
                        dout.writeChars("vplayer Forward"+'\n');
                   else if(vlcoption.getSelectedIndex()==9)
                        dout.writeChars("vplayer Rewind"+'\n');
                    else if(vlcoption.getSelectedIndex()==10)
                        dout.writeChars("vplayer Mute"+'\n');
                    else if(vlcoption.getSelectedIndex()==11)
                        dout.writeChars("vplayer Aratio"+'\n');
                   else if(vlcoption.getSelectedIndex()==12)
                        dout.writeChars("vplayer Cscreen"+'\n');
                       else if(vlcoption.getSelectedIndex()==13)
                        dout.writeChars("vplayer Fscreen"+'\n');
                    else if(vlcoption.getSelectedIndex()==14)
                        dout.writeChars("vplayer Exit"+'\n');
                       dout.flush();
               } 
              catch(Exception e){
                  
              }  
            }
            
             if(dis == selectkeys2)
            {
               try{
                  
                    if(selectkeys2.getSelectedIndex()==0)
                        dout.writeChars("movekey tab"+'\n');
                    else if(selectkeys2.getSelectedIndex()==1)
                        dout.writeChars("movekey back"+'\n');
                   else if(selectkeys2.getSelectedIndex()==2)
                        dout.writeChars("movekey down"+'\n');
                   else  if(selectkeys2.getSelectedIndex()==3)
                        dout.writeChars("movekey up"+'\n');
                    else  if(selectkeys2.getSelectedIndex()==4)
                        dout.writeChars("movekey right"+'\n');
                    else  if(selectkeys2.getSelectedIndex()==5)
                        dout.writeChars("movekey left"+'\n');
                    else  if(selectkeys2.getSelectedIndex()==6)
                        dout.writeChars("movekey ok"+'\n');
                    else  if(selectkeys2.getSelectedIndex()==7)
                        dout.writeChars("movekey sall"+'\n');
                     else  if(selectkeys2.getSelectedIndex()==8)
                        dout.writeChars("movekey menubar"+'\n');
                     else  if(selectkeys2.getSelectedIndex()==9)
                        dout.writeChars("movekey foption"+'\n');
                    else  if(selectkeys2.getSelectedIndex()==10)
                        dout.writeChars("movekey escape"+'\n');
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
            
             
            if(dis==pwroption)
            {
               try{
                  
                   if(pwroption.getSelectedIndex()==0)
                        dout.writeChars("power POWERPNT.exe"+'\n');
                   else  if(pwroption.getSelectedIndex()==1)
                        dout.writeChars("power ofile"+'\n');
                    else  if(pwroption.getSelectedIndex()==2)
                    {
                      display.setCurrent(selectkeys3);
                    }
                   else if(pwroption.getSelectedIndex()==3)
                        dout.writeChars("power exit"+'\n');
                   else  if(pwroption.getSelectedIndex()==4)
                        dout.writeChars("power next"+'\n');
                    else  if(pwroption.getSelectedIndex()==5)
                        dout.writeChars("power previous"+'\n');
                    else  if(pwroption.getSelectedIndex()==6)
                        dout.writeChars("power Close"+'\n');
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
            
            
             if(dis == selectkeys3)
            {
               try{
                  
                    if(selectkeys3.getSelectedIndex()==0)
                        dout.writeChars("movekey tab"+'\n');
                    else if(selectkeys3.getSelectedIndex()==1)
                        dout.writeChars("movekey back"+'\n');
                   else if(selectkeys3.getSelectedIndex()==2)
                        dout.writeChars("movekey down"+'\n');
                   else  if(selectkeys3.getSelectedIndex()==3)
                        dout.writeChars("movekey up"+'\n');
                    else  if(selectkeys3.getSelectedIndex()==4)
                        dout.writeChars("movekey right"+'\n');
                    else  if(selectkeys3.getSelectedIndex()==5)
                        dout.writeChars("movekey left"+'\n');
                    else  if(selectkeys3.getSelectedIndex()==6)
                        dout.writeChars("movekey ok"+'\n');
                    else  if(selectkeys3.getSelectedIndex()==7)
                        dout.writeChars("movekey sall"+'\n');
                     else  if(selectkeys3.getSelectedIndex()==8)
                        dout.writeChars("movekey menubar"+'\n');
                     else  if(selectkeys3.getSelectedIndex()==9)
                        dout.writeChars("movekey foption"+'\n');
                    else  if(selectkeys3.getSelectedIndex()==10)
                        dout.writeChars("movekey escape"+'\n');
                    dout.flush();
                    } 
              catch(Exception e){
                  
              }   
            }
           
            
            if(dis == runmode)
            {
                try     {
                if(runmode.getSelectedIndex() == 0)
                   display.setCurrent(cmd); 
                else if(runmode.getSelectedIndex() == 1)
                {
                dout.writeChars("run shutdown"+'\n');
                dout.flush();
                cmd.setString("");
                Alert a=new Alert("Success","Command sent successfuly.",null,AlertType.INFO);
                a.setTimeout(700);
                display.setCurrent(a);
                }
                else if(runmode.getSelectedIndex() == 2)
                {
                dout.writeChars("run restart"+'\n');
                dout.flush();
                cmd.setString("");
                Alert a=new Alert("Success","Command sent successfuly.",null,AlertType.INFO);
                a.setTimeout(700);
                display.setCurrent(a);
                }
                else if(runmode.getSelectedIndex() == 3)
                {
                dout.writeChars("run logoff"+'\n');
                dout.flush();
                cmd.setString("");
                Alert a=new Alert("Success","Command sent successfuly.",null,AlertType.INFO);
                a.setTimeout(700);
                display.setCurrent(a);
                }
                          }
                catch(Exception e)
                {
                    
                }
             }
        }
    
    }

    
    
    public void FindDevices() {
        try {
            devices = new java.util.Vector();  /*.......Vector implements a dynamic array
														Vector proves to be very useful if you don't know the size of the array in advance or 
														you just need one that can change sizes over the lifetime of a program.
			*/
            LocalDevice local = LocalDevice.getLocalDevice();
            DiscoveryAgent agent = local.getDiscoveryAgent();   /* agent is DiscoveryAgent Object which is used for discovery to client */

            agent.startInquiry(DiscoveryAgent.GIAC, this);   /*............DiscoveryAgent.GIAC specifies General Inquiry Access Code. 
																		No limit is set on how long the device remains in the discoverable mode.*/   
        } catch (Exception e) {
            this.do_alert("Error in initiating Inquiry", 4000);
        }
    }
    
    
    
    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
	
	
	/*retrieve the device that is at the other end of the Bluetooth Serial Port Profile connection, L2CAP connection,
	or OBEX over RFCOMM connection
    RemoteDevice remote = RemoteDevice.getRemoteDevice(javax.microedition.io.Connection c);*/
	
        devices.addElement(remoteDevice);
    }
    
      public void inquiryCompleted(int param) {
          int no=0;
        switch (param) {
            case DiscoveryListener.INQUIRY_COMPLETED: //Inquiry completed normally.
                if (devices.size() > 0) { //Atleast one device has been found, device "Vector" class object.
                    services = new java.util.Vector();
                   // this.FindServices((RemoteDevice) devices.elementAt(0)); //Check if the first device offers the service
                    while(no<=(devices.size()-1))
                    {
                    try {
            dev_list.append(((RemoteDevice) devices.elementAt(no++)).getFriendlyName(true), null);
       } catch (Exception e) {
          // this.do_alert("Erron in initiating search", 4000);
       }
                    }
                    display.setCurrent(dev_list);
                } else {
                    do_alert("No device found in range", 4000);
                }
                break;
            case DiscoveryListener.INQUIRY_ERROR: // Error during inquiry
                this.do_alert("Inqury error", 4000);
                break;
            case DiscoveryListener.INQUIRY_TERMINATED: // Inquiry terminated by agent.cancelInquiry()
                this.do_alert("Inqury Canceled", 4000);
                break;
        }
    }

	//Client Start it's Bluetooth connection.....
	
    public void FindServices(RemoteDevice device) {
        try {
		//Define the Server Socket for Matching with server...
		
            UUID[] uuids = new UUID[1];
           uuids[0] = new UUID("00000102030405060740A1B1C1D1E100",false); //The UUID is a socket for server match with server side....
		//Initilize the local device...   
            local = LocalDevice.getLocalDevice();
			
			//Put it's Agent for Dscovery...
            agent = local.getDiscoveryAgent();
			
			//Search for Same UUID witch is mention in Client Side....
            agent.searchServices(null,uuids,device,this);  /* "agent" search for UUID which is on server side, search 'RemoteDevice device'*/
        } catch (Exception e) {
            this.do_alert("Erron in initiating search", 4000);
        }
    }

    
	//After Identify the Server(After Discovery).....
    public void servicesDiscovered(int transID, ServiceRecord[] serviceRecord) {
        for (int x = 0; x < serviceRecord.length; x++) {
            services.addElement(serviceRecord[x]);
        }
        
       /* try {
            dev_list.append(((RemoteDevice) devices.elementAt(currentDevice)).getFriendlyName(false), null);
        } catch (Exception e) {
            this.do_alert("Erron in initiating search", 4000);
        }*/
    }

  

    public void serviceSearchCompleted(int transID, int respCode) {
        switch (respCode) {
            case DiscoveryListener.SERVICE_SEARCH_COMPLETED:
                if (services.size() > 0) {
                        ServiceRecord service = (ServiceRecord) services.elementAt(0);
                        String url = service.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,false);
                try {
                    conn = (StreamConnection) Connector.open(url); //establish the connection
                    din = new DataInputStream(conn.openInputStream());
                    dout = new DataOutputStream(conn.openOutputStream());//Get the output stream
                    display.setCurrent(choosemode); 
                    } catch (Exception e) {
                    this.do_alert("Error Connecting", 4000);
                    }
                        
                        //display.setCurrent(dev_list);
                    } else 
                    {
                        do_alert("The service was not found", 4000);
                    }
                break;
           // the device specified in the search request could not be reached or the local device could not establish a connection to the remote device     
            case DiscoveryListener.SERVICE_SEARCH_DEVICE_NOT_REACHABLE:
                this.do_alert("Device not Reachable", 2000);
                break;
                // an error occurred while processing the request
            case DiscoveryListener.SERVICE_SEARCH_ERROR:
                this.do_alert("Service serch error", 4000);
                break;
            case DiscoveryListener.SERVICE_SEARCH_NO_RECORDS:  // no records were found during the service search
                this.do_alert("No records returned", 2000);
                break;
            case DiscoveryListener.SERVICE_SEARCH_TERMINATED:
                this.do_alert("Inquiry Cancled", 4000);
                break;
        }
    }

    public void do_alert(String msg, int time_out) {
           if (display.getCurrent() instanceof Alert) {
              ((Alert) display.getCurrent()).setString(msg);
              ((Alert) display.getCurrent()).setTimeout(time_out);
              } 
           else
           {
            Alert alert = new Alert("Bluetooth");
            alert.setString(msg);
            alert.setTimeout(time_out);
            display.setCurrent(alert);
           }
         }
   
    public void pauseApp() {
                               notifyPaused();
                           }

    public void destroyApp(boolean unconditional) {
    }
}
