import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import static java.lang.Math.*;

/**
 * Created by X on 8/12/2017.
 */
public class Game extends BasicGame {

    float playerX = 200;
    float playerY = 200;
    int viewDist = 200;
    int moveStep = 1;
    int viewingDirectionInDegrees = 0;
    float fov = 60;
    double distanceFromPlayerToProjectionPlane;
    double angleBetweenSubsequentRays;
    float projectedSliceHeight;
    float wallHeight = 64;
    int centerOfProjectionPlane;

    Game(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        angleBetweenSubsequentRays = fov / gameContainer.getWidth();
        distanceFromPlayerToProjectionPlane = gameContainer.getWidth() / tan(fov/2);
        centerOfProjectionPlane = gameContainer.getHeight()/2;
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        if(gameContainer.getInput().isKeyDown(Input.KEY_A)){
            viewingDirectionInDegrees -=1;
        }
        if(gameContainer.getInput().isKeyDown(Input.KEY_D)){
            viewingDirectionInDegrees +=1;
        }
        if(gameContainer.getInput().isKeyDown(Input.KEY_W)){
            playerX = playerX + (float)Math.cos(toRadians(viewingDirectionInDegrees)) * moveStep;
            playerY = playerY + (float)Math.sin(toRadians(viewingDirectionInDegrees)) * moveStep;
        }
        if(gameContainer.getInput().isKeyDown(Input.KEY_S)){
            playerX = playerX - (float)Math.cos(toRadians(viewingDirectionInDegrees)) * moveStep;
            playerY = playerY - (float)Math.sin(toRadians(viewingDirectionInDegrees)) * moveStep;
        }
    }

    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {


     //   float endX = playerX + (float)Math.cos(toRadians(viewingDirectionInDegrees)) * viewDist;
    //    float endY = playerY + (float)Math.sin(toRadians(viewingDirectionInDegrees)) * viewDist;
//
      //  Line line = new Line(playerX,playerY,endX,endY);
     //   graphics.draw(line);


        Rectangle rect1 = new Rectangle(320,320,64,64);
      //  Rectangle rect2 = new Rectangle(200,300,40,40);
        graphics.draw(rect1);
       // graphics.draw(rect2);
        GeomUtil geomUtil = new GeomUtil();

        //number of rays
        int numberOfRays = gameContainer.getWidth();


       // System.out.println(numberOfRays);


       // System.out.println(angleBetweenSubsequentRays);


        int counterX = 0;
        for(float angle = -fov/2; angle < fov/2; angle += angleBetweenSubsequentRays){
            float endX = playerX + (float)Math.cos(toRadians(viewingDirectionInDegrees + angle)) * viewDist;
            float endY = playerY + (float)Math.sin(toRadians(viewingDirectionInDegrees + angle)) * viewDist;
            Line line = new Line(playerX,playerY,endX,endY);
            if(line.intersects(rect1)){
                GeomUtil.HitResult hitResult = geomUtil.intersect(rect1,line);
                line.set(playerX,playerY, hitResult.pt.getX(),hitResult.pt.getY());
                //System.out.println(line.length());
                double rawDistanceToSlice = line.length();
                System.out.println(angle);
                //where BETA is the angle of the ray that is being cast relative to the viewing angle.
               // double relativeAngle =
                //double correctDistanceToSlice = rawDistanceToSlice * cos((Math.abs(angle)));
                //System.out.println(correctDistanceToSlice);

                //projectedSliceHeight = wallHeight / (float)rawDistanceToSlice * (float)distanceFromPlayerToProjectionPlane;
                projectedSliceHeight = wallHeight / (float)rawDistanceToSlice * (float)distanceFromPlayerToProjectionPlane;

                //view Dist = 200,
                //if view dist 0 wall height max
                // if view dist 200 min
                //int wallHeight = 64 / (int)correctDistanceToSlice * 277;
                //System.out.println(line.length());

                graphics.drawLine(counterX,centerOfProjectionPlane-projectedSliceHeight/2,counterX,centerOfProjectionPlane+projectedSliceHeight/2);
            }

            graphics.draw(line);
            counterX++;
           // System.out.println(counterX);
        }


    }
}
