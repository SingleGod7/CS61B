public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public static double G = 6.67e-11;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName){
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet b){
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(Planet otherPlanet){
        double dx = otherPlanet.xxPos - this.xxPos;
        double dy = otherPlanet.yyPos - this.yyPos;
        double r = Math.sqrt(dx*dx + dy*dy);
        return r;
    }

    public double calcForceExertedBy(Planet otherPlanet){
        double r = this.calcDistance(otherPlanet);
        double f = Planet.G * this.mass * otherPlanet.mass /(r * r);
        return f;
    }

    public double calcForceExertedByX(Planet otherPlanet){
        double dx = otherPlanet.xxPos - this.xxPos;
        double r = this.calcDistance(otherPlanet);
        double f = this.calcForceExertedBy(otherPlanet);
        return f * dx / r;
    }

    public double calcForceExertedByY(Planet otherPlanet){
        double dy = otherPlanet.yyPos - this.yyPos;
        double r = this.calcDistance(otherPlanet);
        double f = this.calcForceExertedBy(otherPlanet);
        return f * dy / r;
    }

    public double calcNetForceExertedByX(Planet[] otherPlanets){
        double totalForce = 0;
        for(int i = 0; i < otherPlanets.length;i++){
            if(this.equals(otherPlanets[i])){
                continue;
            }
            totalForce += this.calcForceExertedByX(otherPlanets[i]);
        }
        return totalForce;
    }

    public double calcNetForceExertedByY(Planet[] otherPlanets){
        double totalForce = 0;
        for(int i = 0; i < otherPlanets.length;i++){
            if(this.equals(otherPlanets[i])){
                continue;
            }
            totalForce += this.calcForceExertedByY(otherPlanets[i]);
        }
        return totalForce;
    }

    public void update(double dt, double fX, double fY){
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        this.xxVel += aX * dt;
        this.yyVel += aY * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    public void draw(){
        StdDraw.picture(this.xxPos,this.yyPos,"images/" + this.imgFileName);
    }
}
