import java.io.IOException;
import java.io.*;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

class LiveDesktop extends Canvas implements CommandListener
{
    
  DataInputStream dout=null;
  DataOutputStream dtout=null;
  StreamConnection conn=null;
  List list;
  Canvas can;
  Display display;
  Command back;
  List device;
  Image image;
  int c,i,len=0;
  Form f;
  static int flag=0,flag1=0,flag2=0;
  int x=getWidth()/2,y=getHeight()/2;

  public LiveDesktop (StreamConnection steam,Display dis,DataOutputStream dtout,DataInputStream din,List list) 
  {
         f=new Form("Help");
         f.append("1]  \'#\' key -> Fullscreen.\n\n");
         f.append("2]  Back -> Selection Mode.");
        
         back=new Command("Back",Command.BACK,1);
         f.addCommand(back);
         f.setCommandListener(this);
         setFullScreenMode(true);
         try {
            dout = din;//Get the output stream
            dtout.writeChars("live"+'\n');
            dtout.flush();
            dtout.writeInt(getWidth());
            dtout.flush();
            dtout.writeInt(getHeight());
            dtout.flush();
           this.dtout=dtout;
            conn=steam;
            this.dtout=dtout;
            display = dis;
            this.list=list;
           
          
           
          } catch (IOException ex) {
          
                                   }
  }
  
  public void paint (Graphics g) 
  {
    g.setGrayScale (0);
    g.fillRect (0, 0, getWidth (), getHeight ());
    try {
            dtout.writeInt(1);
            dtout.flush();
    }
    catch(IOException e)
    {
        
    }
    if(flag2==0)
     {
        try {
             len=dout.readInt();    
             byte[] bt=new byte[len];
             dout.readFully(bt,0,len);
             InputStream in = new ByteArrayInputStream(bt);
             image=Image.createImage(in);
             g.drawImage (image, x, y,Graphics.HCENTER | Graphics.VCENTER); 
            } catch (Exception ex) {
                      ex.printStackTrace();
                    }
        g.setColor(180,180,180);
    g.fillRect (0,getHeight()-20,getWidth(),20);
    g.setGrayScale(0);
    g.drawString("Help",2,getHeight()-2,Graphics.BOTTOM | Graphics.LEFT);
    g.drawString("Back",getWidth()-5,getHeight()-2,Graphics.BOTTOM | Graphics.RIGHT);
     }
     else
     {
         try {
        
             len=dout.readInt();    
             byte[] bt=new byte[len];
             dout.readFully(bt,0,len);
             InputStream in = new ByteArrayInputStream(bt);
             image=Image.createImage(in);
             image=rotateImage(image,90);
             g.drawImage (image, 5, 0,Graphics.LEFT | Graphics.TOP); 
         }
         catch(Exception e)
         {}
        
     }
    if(flag1==0)
     repaint();
    else 
    {
          try {
              dtout.writeInt(0);
              dtout.flush();
              display.setCurrent(list);
          } catch (IOException ex) {
              ex.printStackTrace();
          }
    }
     
    }

  public static Image rotateImage(Image image, int angle) throws Exception
{
	if(angle == 0)
	{
		return image; 
	}
        
	int width = image.getWidth();
	int height = image.getHeight();
 
	int[] rowData = new int[width];
	int[] rotatedData = new int[width * height];
 
	int rotatedIndex = 0;
 
	for(int i = 0; i < height; i++)
	{
		image.getRGB(rowData, 0, width, 0, i, width, 1);
 
		for(int j = 0; j < width; j++)
		{
			rotatedIndex = 
				angle == 90 ? (height - i - 1) + j * height : 
				(angle == 270 ? i + height * (width - j - 1) : 
					width * height - (i * width + j) - 1
				);
 
			rotatedData[rotatedIndex] = rowData[j];
		}
	}
 
	if(angle == 90 || angle == 270)
	{
		return Image.createRGBImage(rotatedData, height, width, true);
	}
	else
	{
		return Image.createRGBImage(rotatedData, width, height, true);
	}
}
  
  protected void keyPressed(int key)
  {
      try {
      switch(key)
      {
      case Canvas.KEY_POUND:
          
          if(flag==0)
          {
          flag2=1;
          flag=1;
          }
          else
          {
              image=rotateImage(image,270);
              flag=0;
              flag2=0;
          }
         
         break;
     case -6:
                   display.setCurrent(f);
                   break;
         
     case -7:
              flag1=1;
              break;
         
    
  }      
  repaint();    
}
      catch(Exception e)
      {}
  }
  
  public void commandAction(Command command, Displayable displayable)
  {
      if(command==back)
      {
              display.setCurrent(can);
              repaint();
         
      } 
  }
  
  public void show(Canvas can)
  {
    this.can=can;
    flag1=0;
  }
   
   public void draw()
   {
     try {
               dtout.writeChars("live"+'\n');
              dtout.flush();
              display.setCurrent(can);
              dtout.writeInt(getWidth());
              dtout.flush();
              dtout.writeInt(getHeight());
              dtout.flush();
              flag1=0;
              repaint();
          } catch (IOException ex) {
              ex.printStackTrace();
          }
   }
