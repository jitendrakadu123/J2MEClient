import java.io.IOException;
import java.io.*;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

class canvas extends Canvas implements CommandListener
{
    
  DataInputStream dout=null;
  DataOutputStream dtout=null;
  StreamConnection conn=null;
  Display display;
  Command back;
  Canvas can;
  List choose;
  int c,i,len=0;
  Form f;
  static int flag=0;
  int x=getWidth()/2,y=getHeight()/2;

  public canvas (StreamConnection steam,Display dis,DataOutputStream dtout,DataInputStream din,List choose) 
  {
         f=new Form("Help");
         back=new Command("Back",Command.BACK,1);
         f.addCommand(back);
         f.setCommandListener(this);
         
         setFullScreenMode(true);
         try {
            dout = din;//Get the output stream
            dtout.writeChars("mouse"+'\n');
            dtout.flush();
            dtout.writeInt(getWidth());
            dtout.flush();
            dtout.writeInt(getHeight());
            dtout.flush();
            conn=steam;
            this.dtout=dtout;
            display = dis;
            this.choose=choose;
           
          } catch (IOException ex) {
          
                                   }
         f.append("1]  Left / 4 - Move Left.\n");
         f.append("2]  Right / 6 - Move Right.\n");
         f.append("3]  Up / 2 - Move Up.\n");
         f.append("4]  Down / 8 - Move Down.\n");
         f.append("5]  Fire or 5 - Selection.\n");
         f.append("6]  3 -> Right Click.");
  }
  
  public void paint (Graphics g) 
  {
    g.setGrayScale (255);
    g.fillRect (0, 0, getWidth (), getHeight ());
    
   
        try {
             len=dout.readInt();    
             byte[] bt=new byte[len];
             dout.readFully(bt,0,len);
             InputStream in = new ByteArrayInputStream(bt);
             Image image=Image.createImage(in);
             g.drawImage (image, x, y,Graphics.HCENTER | Graphics.VCENTER); 
            } catch (IOException ex) {
                      ex.printStackTrace();
                    }
    
    
    g.setColor(180,180,180);
    g.fillRect (0,getHeight()-20,getWidth(),20);
    g.setGrayScale(0);
    g.drawString("Help",2,getHeight()-2,Graphics.BOTTOM | Graphics.LEFT);
    g.drawString("Back",getWidth()-5,getHeight()-2,Graphics.BOTTOM | Graphics.RIGHT);
    flag=1;
  }
  
  protected void keyPressed(int key)
  {
      try{
            switch ( getGameAction(key) )
            {
     
                case Canvas.UP:  
                case Canvas.KEY_NUM2:
                         dtout.writeChars("up"+'\n');
                         dtout.flush();
                         break;
               
               case Canvas.DOWN:
               case Canvas.KEY_NUM8:
                         dtout.writeChars("down"+'\n');
                         dtout.flush();
                         break;
        
                case Canvas.LEFT:
                case Canvas.KEY_NUM4:
                         dtout.writeChars("left"+'\n');
                         dtout.flush();
                         break;
               
                case Canvas.RIGHT:
                case Canvas.KEY_NUM6:
                         dtout.writeChars("right"+'\n');
                         dtout.flush();
                         break;
         
                case Canvas.FIRE:
                case Canvas.KEY_NUM5:
                         dtout.writeChars("fire"+'\n');
                         dtout.flush();
                         break;      
          }
 
          switch(key)
          {   
       
                case -6:
                          display.setCurrent(f);
                          destroyApp(true);
                          break;
         
                case -7:
                           flag=0;
                           display.setCurrent(choose);
                           break;
         
                case Canvas.KEY_NUM3:
                            dtout.writeChars("rclick"+'\n');
                            dtout.flush();
                            break;     
            
         }
   }
   catch (IOException ex) {
                    ex.printStackTrace();
                    }
   repaint();
 }
  
  public void destroyApp(boolean unconditional)
  {
      
  }
  
   public void show(Canvas can)
  {
    this.can=can;
  }
   
   public void draw()
   {
     try {
               dtout.writeChars("mouse"+'\n');
              dtout.flush();
              display.setCurrent(can);
              dtout.writeInt(getWidth());
              dtout.flush();
              dtout.writeInt(getHeight());
              dtout.flush();
              repaint();
          } catch (IOException ex) {
              ex.printStackTrace();
          }
   }
  
  public void commandAction(Command command, Displayable displayable)
  {
      if(command==back)
      {
          try {
               dtout.writeChars("mouse"+'\n');
              dtout.flush();
              display.setCurrent(can);
              dtout.writeInt(getWidth());
              dtout.flush();
              dtout.writeInt(getHeight());
              dtout.flush();
              repaint();
          } catch (IOException ex) {
              ex.printStackTrace();
          }
      }
      
  }
}