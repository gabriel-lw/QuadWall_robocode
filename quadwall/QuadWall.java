package quadwall;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * QuadWall - a robot by gabriel-lw, ViniDefreyn and angeloyuna
 */
public class QuadWall extends Robot
{
	/**
	 * run: QuadWall's default behavior
	 */
	
	// Método que direciona o robô a uma certa quantidade dependendo de seu ângulo
	public void turn(double turn1, double turn2,  double turn3, double turn4) {
			
		if (getHeading() == 0) {
			turnRight(turn1);
		} else if (getHeading() == 90) {
			turnRight(turn2);
		} else if (getHeading() == 180) {
			turnRight(turn3);
		} else {
			turnRight(turn4);
		}
	}


	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

			double moveDistance;
			double distanceWestWall, distanceEastWall, distanceSouthWall, distanceNorthWall;
	
			moveDistance = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
			
			distanceWestWall = getX();
			distanceEastWall = getBattleFieldWidth() - getX();
			distanceSouthWall = getY();
			distanceNorthWall = getBattleFieldHeight() - getY();

			// Se estiver mais próximo da borda oeste, direcionar para ela
			if (distanceWestWall < distanceEastWall && distanceWestWall < distanceNorthWall && distanceWestWall < distanceSouthWall) {
				turnLeft(getHeading() % 90);
				turn(-90, 180, 90, 0);
			}
			
			// Se estiver mais próximo da borda leste, direcionar para ela
			else if (distanceEastWall < distanceWestWall && distanceEastWall < distanceNorthWall && distanceEastWall < distanceSouthWall) {
				turnLeft(getHeading() % 90);
				turn(90, 0, -90, 180);
			}
			
			// Se estiver mais próximo da borda norte, direcionar para ela
			else if (distanceNorthWall < distanceSouthWall && distanceNorthWall < distanceEastWall && distanceNorthWall < distanceWestWall) {
				turnLeft(getHeading() % 90);
				turn(0, -90, 180, 90);
			}
			
			// Se estiver mais próximo da borda sul, direcionar para ela
			else {
				turnLeft(getHeading() % 90);
				turn(180, 90, 0, -90);
			}

			ahead(moveDistance);
			
			setAdjustGunForRobotTurn(true);

			turnRight(90);

			turnGunRight(180);

			// Necessário implementar uma solução nos casos em que o robô se colide com um robô ao se mover para uma borda
			
		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			doNothing();
		
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
}
