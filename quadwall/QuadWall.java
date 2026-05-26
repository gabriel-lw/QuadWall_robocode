package quadwall;
import robocode.*;
import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;
import java.util.ArrayList;
import robocode.HitRobotEvent;
import robocode.util.Utils;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * QuadWall - a robot by gabriel-lw, ViniDefreyn and angeloyuna
 */
public class QuadWall extends AdvancedRobot
{
	double fieldWidth;
	double fieldHeight;
	double[][] trackList =  new double[8][2];
	double[][] trackQuad = new double[3][2];
	int idNextTrack =0; //referente a trackList, salva o quadrante atual em relaçao a todos
	int nextQuadTrackId; //referente a trackQuad, salva o trilho atual dentro de um quadrante especifico
	double nextX;
	double nextY;
	double DR = 50; //distancia razoavel da borda que nao permite passagem de inimigos
	double robotEnergy;
	double robotX;
	double robotY;
	double robotAng;
	double gunAng;
	double radarAng;
	int sentido =1; //sentido indo ou voltando da trackQuad
	public boolean isOnHorizontalBorder;
	public boolean isOnVerticalBorder;
	String lastFiredName= " ";
	int fireConfidence=0;

	// Inicializa as variáveis da quantidade de robôs em cada quadrante e a lista dos robôs já escaneados
	double numFirstQuadrant = 0;
	double numSecondQuadrant = 0;
	double numThirdQuadrant = 0;
	double numFourthQuadrant = 0;
	ArrayList<String> names = new ArrayList<>();
	ArrayList<String> firstQuadrantNames = new ArrayList<>();
	ArrayList<String> secondQuadrantNames = new ArrayList<>();
	ArrayList<String> thirdQuadrantNames = new ArrayList<>();
	ArrayList<String> fourthQuadrantNames= new ArrayList<>();

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
				firstQuadrantNames.add(e.getName());
			
				if (secondQuadrantNames.contains(e.getName())) {
					secondQuadrantNames.remove(e.getName());
				}
				
				else if (thirdQuadrantNames.contains(e.getName())) {
					thirdQuadrantNames.remove(e.getName());
				}
				
				else if (fourthQuadrantNames.contains(e.getName())) {
					fourthQuadrantNames.remove(e.getName());
				}			
				
			}
			
		
			// Se oponente robô estiver no segundo quadrante
			else if (opponentX > quadrantBorderX && opponentY > quadrantBorderY) {
				numSecondQuadrant += +1;
				System.out.println("Robôs no segundo quadrante: " + numSecondQuadrant);	
				secondQuadrantNames.add(e.getName());
			
				if (firstQuadrantNames.contains(e.getName())) {
					firstQuadrantNames.remove(e.getName());
				}
				
				else if (thirdQuadrantNames.contains(e.getName())) {
					thirdQuadrantNames.remove(e.getName());
				}
				
				else if (fourthQuadrantNames.contains(e.getName())) {
					fourthQuadrantNames.remove(e.getName());
				}

			}	

			// Se oponente robô estiver no terceiro quadrante
			else if (opponentX < quadrantBorderX && opponentY < quadrantBorderY) {
				numThirdQuadrant += +1;
				System.out.println("Robôs no terceiro quadrante: " + numThirdQuadrant);
				thirdQuadrantNames.add(e.getName());
			
				if (firstQuadrantNames.contains(e.getName())) {
					firstQuadrantNames.remove(e.getName());
				}
					
				else if (secondQuadrantNames.contains(e.getName())) {
					secondQuadrantNames.remove(e.getName());
				}
				
				else if (thirdQuadrantNames.contains(e.getName())) {
					fourthQuadrantNames.remove(e.getName());
				}
				
			}
		
			// Se oponente robô estiver no quarto quadrante
			else if (opponentX > quadrantBorderX && opponentY < quadrantBorderY) {
				numFourthQuadrant += +1;
				System.out.println("Robôs no quarto quadrante: " + numFourthQuadrant);
				fourthQuadrantNames.add(e.getName());
				
				if (firstQuadrantNames.contains(e.getName())) {
					firstQuadrantNames.remove(e.getName());
				}
				
				else if (secondQuadrantNames.contains(e.getName())) {
					secondQuadrantNames.remove(e.getName());
				}
				
				else if (thirdQuadrantNames.contains(e.getName())) {
					thirdQuadrantNames.remove(e.getName());
				}
				
			}
			names.add(e.getName());
		}	
	}

	public void scanNextQuadrant() {
	
			numFirstQuadrant = 0;
			numSecondQuadrant = 0;
			numThirdQuadrant = 0;
			numFourthQuadrant = 0;
			names.clear();
	
			turnGunRight(120);
			turnGunLeft(240);
			turnGunRight(120);


			if ((numFirstQuadrant > numSecondQuadrant) && (numFirstQuadrant > numThirdQuadrant) && (numFirstQuadrant > numFourthQuadrant)) {
				System.out.println("Quadrante a se mover: Primeiro");
				/// entrar no subarray correspondente ao quadrantre, analisar as 3 posicoes e achar a mais proxima da atual
				// nao permitir movimentaçao entre quadrantes nao adjacentes 
				

			} else if ((numSecondQuadrant > numThirdQuadrant) && (numSecondQuadrant > numFourthQuadrant)) {
				System.out.println("Quadrante a se mover: Segundo");

			} else if (numThirdQuadrant > numFourthQuadrant) {
				System.out.println("Quadrante a se mover: Terceiro");			

			} else {
				System.out.println("Quadrante a se mover: Quarto");
			}
	}
	
	/**
	 * run: QuadWall's default behavior
	 */	
	public void run() {
	
		
			robotX = getX();
			robotY = getY();
			robotAng = getHeading();
			fieldWidth = getBattleFieldWidth();
			fieldHeight = getBattleFieldHeight();
			
			//definiçao dos trilhos (da borda inicial)
		    trackList[0][0] = DR;
			trackList[0][1] = DR;
			
			trackList[1][0] = DR;
			trackList[1][1] = fieldHeight/2;
			
			trackList[2][0] = DR;
			trackList[2][1] = fieldHeight - DR;
			
			trackList[3][0] = fieldWidth/2;
			trackList[3][1] = fieldHeight - DR;
			
			trackList[4][0] = fieldWidth - DR;
			trackList[4][1] = fieldHeight - DR;
			
			trackList[5][0] = fieldWidth - DR;
			trackList[5][1] = fieldHeight/2;
			
			trackList[6][0] = fieldWidth - DR;
			trackList[6][1] = DR;
			
			trackList[7][0] = fieldWidth/2;
			trackList[7][1] = DR;
			
			double moveDistance;
			double distanceWestWall, distanceEastWall, distanceSouthWall, distanceNorthWall;
				
			distanceWestWall = getX();
			distanceEastWall = getBattleFieldWidth() - getX();
			distanceSouthWall = getY();
			distanceNorthWall = getBattleFieldHeight() - getY();

			// Se estiver mais próximo da borda oeste, direcionar para ela
			if (distanceWestWall < distanceEastWall && distanceWestWall < distanceNorthWall && distanceWestWall < distanceSouthWall) {
				turnLeft(getHeading() % 90);
				turn(-90, 180, 90, 0);
				moveDistance = distanceWestWall -50;
				//definir o ponto de inicio do track de acordo com a borda
				if(distanceSouthWall <= distanceNorthWall )
				{
					//vai para oeste sul, quad 3  ids 1, 0 , 7    
				
					trackQuad[0][0] = trackList[1][0];
					trackQuad[0][1] = trackList[1][1];
					trackQuad[1][0] = trackList[0][0];
					trackQuad[1][1] = trackList[0][1];
					trackQuad[2][0] = trackList[7][0];
					trackQuad[2][1] = trackList[7][1];
					
				}else{
					//vai para oeste norte
					//quad 1 ids 1, 2 ,3 
					trackQuad[0][0] = trackList[1][0];
					trackQuad[0][1] = trackList[1][1];
					trackQuad[1][0] = trackList[2][0];
					trackQuad[1][1] = trackList[2][1];
					trackQuad[2][0] = trackList[3][0];
					trackQuad[2][1] = trackList[3][1];
					
					
				}
				
			}
			
			// Se estiver mais próximo da borda leste, direcionar para ela
			else if (distanceEastWall < distanceWestWall && distanceEastWall < distanceNorthWall && distanceEastWall < distanceSouthWall) {
				turnLeft(getHeading() % 90);
				turn(90, 0, -90, 180);
				moveDistance = distanceEastWall -50;
				
				if(distanceSouthWall <= distanceNorthWall )
				{
					//vai para leste sul, quad 2  ids 7, 6 , 5
				
					trackQuad[0][0] = trackList[5][0];
					trackQuad[0][1] = trackList[5][1];
					trackQuad[1][0] = trackList[6][0];
					trackQuad[1][1] = trackList[6][1];
					trackQuad[2][0] = trackList[7][0];
					trackQuad[2][1] = trackList[7][1];
					
				}else{
					//vai para leste norte, quad 2 ids 5, 4 ,3 
				
					trackQuad[0][0] = trackList[5][0];
					trackQuad[0][1] = trackList[5][1];
					trackQuad[1][0] = trackList[4][0];
					trackQuad[1][1] = trackList[4][1];
					trackQuad[2][0] = trackList[3][0];
					trackQuad[2][1] = trackList[3][1];
					
					
				}

				//repetiçao de codigo arrumar dps
			
			}
			
			// Se estiver mais próximo da borda norte, direcionar para ela
			else if (distanceNorthWall < distanceSouthWall && distanceNorthWall < distanceEastWall && distanceNorthWall < distanceWestWall) {
				turnLeft(getHeading() % 90);
				turn(0, -90, 180, 90);
				moveDistance = distanceNorthWall -50;
				
				if(distanceWestWall <= distanceEastWall )
				{
					//vai para norte oeste, quad 0  ids 3, 2 , 1     
				
					trackQuad[0][0] = trackList[3][0];
					trackQuad[0][1] = trackList[3][1];
					trackQuad[1][0] = trackList[2][0];
					trackQuad[1][1] = trackList[2][1];
					trackQuad[2][0] = trackList[1][0];
					trackQuad[2][1] = trackList[1][1];
					
				}else{
					//quad 1 ids 3, 4 ,5 
					//vai para norte leste
					trackQuad[0][0] = trackList[3][0];
					trackQuad[0][1] = trackList[3][1];
					trackQuad[1][0] = trackList[4][0];
					trackQuad[1][1] = trackList[4][1];
					trackQuad[2][0] = trackList[5][0];
					trackQuad[2][1] = trackList[5][1];
					
					
				}
				
			}
			
			// Se estiver mais próximo da borda sul, direcionar para ela
			else {
				turnLeft(getHeading() % 90);
				turn(180, 90, 0, -90);
				moveDistance = distanceSouthWall -50;
				
				if(distanceWestWall <= distanceEastWall )
				{
					//vai para oeste sul, quad 3  ids 7, 0 , 1   
				
					trackQuad[0][0] = trackList[7][0];
					trackQuad[0][1] = trackList[7][1];
					trackQuad[1][0] = trackList[0][0];
					trackQuad[1][1] = trackList[0][1];
					trackQuad[2][0] = trackList[1][0];
					trackQuad[2][1] = trackList[1][1];
					
				}else{
					// vai para leste sul, quad 2 ids 7, 6 ,5 
				
					trackQuad[0][0] = trackList[7][0];
					trackQuad[0][1] = trackList[7][1];
					trackQuad[1][0] = trackList[6][0];
					trackQuad[1][1] = trackList[6][1];
					trackQuad[2][0] = trackList[5][0];
					trackQuad[2][1] = trackList[5][1];
					
					
				}
			}
			

			ahead(moveDistance);
			
			setAdjustGunForRobotTurn(true);

			turnRight(90);
			
			getNextPosition();

			// Necessário implementar uma solução nos casos em que o robô se colide com um robô ao se mover para uma borda
			
		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like

			adjustGunHeading();			

			robotX = getX();
			robotY = getY();
			robotAng = getHeading();
			calcBorderExis();
			
			//como o movimento esta definido pela distancia exata do nextX e nextY, a margem de erro nao influencia mais a distancia
			if(robotX >= nextX-45 && robotX<= nextX+45 && robotY >= nextY-45 && robotY <= nextY+45)
			{
				getNextPosition();
				//para a futura troca de quadrante
				// if(changeCurrentQuadrant == true)
				// getnextPosition para a borda mais proxima do proximo quadrante de interesse	
			}
			GoTo(nextX,nextY);

		}
	}
	void adjustGunHeading() {
			boolean nearWestWall = getX() <= 65;
			boolean nearEastWall = getBattleFieldWidth() - getX() <= 65;
			boolean nearSouthWall = getY() <= 65;
			boolean nearNorthWall = getBattleFieldHeight() - getY() <= 65;

			// Se estiver ao lado da borda norte
			while ((!(nearWestWall) && !(nearEastWall) && nearNorthWall) && (!(getGunHeading() >= 179.0 && getGunHeading() <= 181.0))) {
				setTurnGunRight(180 - getGunHeading());
				execute();
			}
			
			// Se estiver ao lado da borda sul,  antes do divisor entre quadrantes 3 e 4
			while ((!(nearWestWall) && !(nearEastWall) && nearSouthWall) && (!(getGunHeading() >= 349.0 || getGunHeading() <= 10.0) && getX() < getBattleFieldWidth() / 2)) {
				setTurnGunLeft(0 + getGunHeading());		
				execute();		
			}
			
			// Se estiver ao lado da borda sul, depois do divisor entre quadrantes 3 e 4
			while ((!(nearWestWall) && !(nearEastWall) && nearSouthWall) && (!(getGunHeading() >= 349.0 || getGunHeading() < 1.0) && getX() > getBattleFieldWidth() / 2)) {
				setTurnGunRight(360 - getGunHeading());		
				execute();		
			}


			// Se estiver ao lado da borde oeste
			while ((!(nearNorthWall) && !(nearSouthWall) && nearWestWall) && (!(getGunHeading() >= 89.0 && getGunHeading() <= 91.0))) {
					setTurnGunRight(90 - getGunHeading());
					execute();
			}
			
			// Se estiver ao lado da borda leste
			while ((!(nearNorthWall) && !(nearSouthWall) && nearEastWall) && (!(getGunHeading() >= 269.0 && getGunHeading() <= 271.00))) {
				setTurnGunRight(270 - getGunHeading());
				execute();
			}
			
			// Se estiver no divisor dos quadrantes 1 e 2
			if (nearNorthWall && (getX() < getBattleFieldWidth() / 2 + 30 && getX() > getBattleFieldWidth() / 2 - 30)) {
				scanNextQuadrant();
			}
			
			// Se estiver no divisor dos quadrantes 3 e 4
			else if (nearSouthWall && (getX() < getBattleFieldWidth() / 2 + 30 && getX() > getBattleFieldWidth() / 2 - 30)) {
				scanNextQuadrant();
				
			}
	}

	void getNextPosition(){
		nextX = trackQuad[idNextTrack][0];
		nextY = trackQuad[idNextTrack][1];
		
		//quando chegar no ultimo ponto do trilho (2), volta de forma decrescente ->(1) ->(0)
		if(idNextTrack >=2){
			//inverte o sentido
			sentido = -1;
		} 
		//quando chegar no limite do trilho (0), volta a subir de forma crescente ->(1) ->(2)
		else if(idNextTrack <= 0)
		{
			//sentido volta ao normal
			sentido=1;
		}
	
		idNextTrack += sentido;
		//redefinir o angulo da arma se ja nao estiver mirando em um inimigo
		
		
	}
	
	void GoTo(double NextX, double NextY)
	{
		//caso use advanced considerar essas variaveis de controle
		//Arrived = false;
		//waitRotation = false;
		
		double moveDistance = (Math.max(robotX, nextX) - Math.min(robotX, nextX) > Math.max(robotY, nextY) - Math.min(robotY, nextY)) ? Math.max(robotX, nextX) - Math.min(robotX, nextX) : Math.max(robotY, nextY) - Math.min(robotY, nextY);
	
		///metodo para calcular e virar para o angulo apenas
		turnAngleToXAndY(NextX, NextY); 
		setAhead(moveDistance);
		execute();		
	}
	
	void turnAngleToXAndY(double NextX, double NextY){
		
		double currentHeading = getHeading();
		double angle = 57.296 * (normalAbsoluteAngle(Math.atan2(NextX- getX(), NextY - getY())));
		double diff = Math.round(currentHeading - angle);
	
		if(diff >0){
			
			if(diff <= 180)
			{
				turnLeft(diff);
			}
			else if(diff > 180)
			{
				turnRight(360 - currentHeading + angle);
			}
		}
		else if(diff < 0){
			
			if(diff < -180)
			{
				turnLeft(currentHeading + 360 - angle);
			}else{
				turnRight(Math.abs(diff));
			}
		}
	}
	

	void followQuadTrack(){
		
		//quando refatorar fazer a definiçao dos pontos do quadrante atual aqui
 
	}
	
	void calcBorderExis(){
		
		double moveDistance;
		double distanceWestWall, distanceEastWall, distanceSouthWall, distanceNorthWall;
			
		distanceWestWall = getX();
		distanceEastWall = getBattleFieldWidth() - getX();
		distanceSouthWall = getY();
		distanceNorthWall = getBattleFieldHeight() - getY();
		double nearest =Math.min(distanceWestWall, Math.min(distanceEastWall, Math.min(distanceSouthWall, distanceNorthWall)));
	
		if(distanceWestWall == nearest || distanceEastWall == nearest){
			//horizontal
			isOnHorizontalBorder =true;
			isOnVerticalBorder =false;
		}
		else{
			//vertical
			isOnHorizontalBorder =false;
			isOnVerticalBorder =true;
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		scanOpponentQuadrant(e);
		double distancia = e.getDistance();
	
		if(distancia < 100){
			fire(3); //16
		}
		else if( distancia < 200)
		{
			if(fireConfidence >= 3){
				fire(3); //16
			}
			else{
				fire(2); //10
			}
		}
		else if(distancia <300)
		{
			if(fireConfidence >= 3){
				fire(2); //10
			}
			else{
				fire(1); //4
			}
		}
		else if( (isOnHorizontalBorder && distancia < getBattleFieldWidth()/2) || (isOnVerticalBorder && distancia < getBattleFieldHeight()/2) ){
				fire(0.5);  
		}
	}
	
	public void onBulletHit(BulletHitEvent e)
	{
		String name = e.getName();
		if(lastFiredName.equals(name))
		{
			fireConfidence++;
		}
		else{
			lastFiredName = name;
			fireConfidence = 1; //nao errou mas nao tem a mesma confiança por ser inimigo diferente
		}
	}
	
	public void onBulletMissed(BulletMissedEvent e) {
		fireConfidence = 0;
		
	}
		
	public void onHitRobot(HitRobotEvent e) {

    // Se fomos nós que batemos no inimigo
    if (e.isMyFault()) {

        // Vira o robô
        setTurnRight(180);

        // Alinha canhão para frente do robô
        setTurnGunRight(Utils.normalRelativeAngleDegrees(getHeading() - getGunHeading()));
        
		// Anda para trás
        setBack(100);

        // Executa tudo simultaneamente
        execute();
    }

    // Se o outro robô bateu em nós
    else {

        // Apenas move o radar para direita
        setTurnGunRight(90);

        execute();
    }
}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
}