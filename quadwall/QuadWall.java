package quadwall;
import robocode.*;
import java.util.ArrayList;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * QuadWall - a robot by gabriel-lw, ViniDefreyn and angeloyuna
 */
public class QuadWall extends Robot
{
	
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
	
	// Inicializa as variáveis da quantidade de robôs em cada quadrante e a lista dos robôs já escaneados
	double numFirstQuadrant = 0;
	double numSecondQuadrant = 0;
	double numThirdQuadrant = 0;
	double numFourthQuadrant = 0;
	ArrayList<String> names = new ArrayList<>();

	// Método que escaneia o quadrante do oponente escaneado
	public void scanOpponentQuadrant(ScannedRobotEvent e) {

		double opponentDistance, opponentX, opponentY, quadrantBorderY, quadrantBorderX;
		double a, b;
		
		// Cálcula as coordenadas do oponente escaneado
		
		opponentDistance = e.getDistance(); 		
		
		a = Math.sin(Math.toRadians(getHeading() + e.getBearing())) * opponentDistance;
		b = Math.cos(Math.toRadians(getHeading() + e.getBearing())) * opponentDistance;
		
		opponentX = Math.abs(getX() + a);
		opponentY = Math.abs(getY() + b);
	
		// Define as bordas de cada quadrante
		quadrantBorderX = getBattleFieldWidth() / 2;
		quadrantBorderY = getBattleFieldHeight() / 2;

		// Se robô oponente já foi escaneado, printar essa mensagem
		if (names.contains(e.getName())) {
			System.out.println("Robô já contado.");
		}
		
		// Se robô oponente já não foi escaneado, verificar qual quadrante ele está
		else {
		
			// Se oponente robô estiver no primeiro quadrante
			if (opponentX < quadrantBorderX &&  opponentY > quadrantBorderY) {
				numFirstQuadrant += +1;
				System.out.println("Robôs no primeiro quadrante: " + numFirstQuadrant);
			}
		
			// Se oponente robô estiver no segundo quadrante
			else if (opponentX > quadrantBorderX && opponentY > quadrantBorderY) {
				numSecondQuadrant += +1;
				System.out.println("Robôs no segundo quadrante: " + numSecondQuadrant);	
			}	

			// Se oponente robô estiver no terceiro quadrante
			else if (opponentX < quadrantBorderX && opponentY < quadrantBorderY) {
				numThirdQuadrant += +1;
				System.out.println("Robôs no terceiro quadrante: " + numThirdQuadrant);
			}
		
			// Se oponente robô estiver no quarto quadrante
			else if (opponentX > quadrantBorderX && opponentY < quadrantBorderY) {
				numFourthQuadrant += +1;
				System.out.println("Robôs no quarto quadrante: " + numFourthQuadrant);
			}
			names.add(e.getName());
		}	
	}

	public void scanNextQuadrant() {
	
			turnGunLeft(90);

			numFirstQuadrant = 0;
			numSecondQuadrant = 0;
			numThirdQuadrant = 0;
			numFourthQuadrant = 0;
			names.clear();

			turnGunRight(180);

			if ((numFirstQuadrant > numSecondQuadrant) && (numFirstQuadrant > numThirdQuadrant) && (numFirstQuadrant > numFourthQuadrant)) {
				System.out.println("Quadrante a se mover: Primeiro");

			} else if ((numSecondQuadrant > numThirdQuadrant) && (numSecondQuadrant > numFourthQuadrant)) {
				System.out.println("Quadrante a se mover: Segundo");

			} else if (numThirdQuadrant > numFourthQuadrant) {
				System.out.println("Quadrante a se mover: Terceiro");			

			} else {
				System.out.println("Quadrante a se mover: Quarto");
			}
			
			turnGunLeft(90);
	}
	
	/**
	 * run: QuadWall's default behavior
	 */	
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
			
			scanNextQuadrant();

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
		scanOpponentQuadrant(e);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
}