package gameElements;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import gameClient.MyGameGUI;
import utils.Point3D;
import gameElements.*;



public class robots implements robot_data{
	
	game_service game;
	graph praph;
	int id;
	int src;
	int dest;
	double speed;
	double value;
	Point3D pos;
	Point3D gui_location;


	public robots(int id, int speed, int src, int dest, Point3D pos, double value) {
		this.id = id;
		this.speed = speed;
		this.src = src;
		this.dest = dest;
		this.pos = pos;
		this.value = value;

	}

	public robots() {}

	
	
	
	public robots(String jsonSTR) {
        this();
        try {
            JSONObject robot = new JSONObject(jsonSTR);
            robot = robot.getJSONObject("Robot");
            double val = robot.getDouble("value");
            int src = robot.getInt("src");
            int id = robot.getInt("id");
            int dst = robot.getInt("dest");
            double speed = robot.getDouble("speed");
            String pos = robot.getString("pos");
            this.value = val;
            this.pos = new Point3D(pos);
            this.src = src;
            this.dest = dst;
            this.speed = speed;
            this.id = id;
            this.setGui_location(0, 0);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
	
	
	
	
	
	
	
	
	
	
	public LinkedList<Integer> robotsInfo(game_service game, graph p) {
		LinkedList<Integer> a= new LinkedList<Integer>();
		try {
			String g = game.getGraph();
			double xscale=0;
			double yscale=0;
			double xmin=Integer.MAX_VALUE;
			double ymin= Integer.MAX_VALUE;
			double xmax=Integer.MIN_VALUE;
			double ymax= Integer.MIN_VALUE;

			JSONObject graph;

			graph = new JSONObject(g);

			JSONArray nodes = graph.getJSONArray("Nodes");

			for (int i = 0; i < nodes.length(); ++i) {//find min x&y foe the scale func
				String pos = nodes.getJSONObject(i).getString("pos");
				String[] str = pos.split(",");
				xscale=Double.parseDouble(str[0]);
				if(xscale<xmin) {
					xmin=xscale;
				}
				if(xscale>xmax) {
					xmax=xscale;
				}

				yscale=Double.parseDouble(str[1]);
				if(yscale<ymin) {
					ymin=yscale;
				}		
				if(yscale>ymax) {
					ymax=yscale;
				}
			}



			for( String robot: game.getRobots())
			{

				 robots robot_tmp = new robots(robot);
		            if (MyGameGUI.km != null) {
		            	MyGameGUI.km.addPlaceMark("dfr", robot_tmp.getLocation().toString());
		            }
				
				
				
				JSONObject ff = new JSONObject(robot);

				JSONObject ttt = ff.getJSONObject("Robot");
				String pos = ttt.getString("pos");
				int id= ttt.getInt("id");
				String[] str = pos.split(",");
				double xxscale=Double.parseDouble(str[0]);
				double yyscale=Double.parseDouble(str[1]);

				int xres =(int) (((xxscale - xmin) / (xmax-xmin)) * (1260 - 40) + 40);
				int yres = 700- (int)(((yyscale - ymin) / (ymax-ymin)) * (660 - 80) + 80);
				a.add(xres);
				a.add(yres);
				a.add(id);


			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}


	@Override
	public void locate(double xmin, double ymin, double ymax, double xmax, LinkedList<Integer> a, game_service game) {
		for( String fruit: game.getRobots())
		{


			JSONObject ff;
			try {
				ff = new JSONObject(fruit);

				JSONObject ttt = ff.getJSONObject("Robot");
				String pos = ttt.getString("pos");
				int id = ttt.getInt("id");
				String[] str = pos.split(",");
				double xxscale = Double.parseDouble(str[0]);
				double yyscale = Double.parseDouble(str[1]);

				int xres = (int) (((xxscale - xmin) / (xmax - xmin)) * (1260 - 40) + 40);
				int yres = (int) (((yyscale - ymin) / (ymax - ymin)) * (660 - 80) + 80);
				a.add(xres);
				a.add(yres);
				a.add(id);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	
	public int getID(robots x) {
		return x.id;
	}

	public void setID(int id) {
		this.id=id;
	}


	public Point3D getPos() {
		return this.pos;
	}
	public void setPos(Point3D pos) {
		this.pos=pos;
	}

	public int getDest() {
		return this.dest;
	}
	public void setDest(int dest) {
		this.dest=dest;
	}

	public int getSrc() {
		return this.src;
	}
	public void setSrc(int src) {
		this.src=src;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value=value;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed=speed;
	}

	 public void setGui_location(double x, double y) {
	        this.gui_location = new Point3D(x, y);
	    }

	 public Point3D getLocation() {
	        return pos;
	    }






}
