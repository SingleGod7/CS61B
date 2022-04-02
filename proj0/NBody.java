public class NBody {
    public static double readRadius(String filePath){
        In in = new In(filePath);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String filePath){
        In in = new In(filePath);
        int cnt = in.readInt();
        double r = in.readDouble();
        Planet[] planets = new Planet[cnt];
        for(int i = 0; i < cnt;i ++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String image = in.readString();
            planets[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,image);
        }
        return planets;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];
        double radius = readRadius(fileName);
        Planet[] planets = readPlanets(fileName);

        StdDraw.enableDoubleBuffering();
        StdAudio.play("audio/2001.mid");

        double time = 0;

        while(time < T){
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for(int i = 0; i < planets.length; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for(int i = 0; i < planets.length; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.setScale(-radius, radius);

            StdDraw.clear();

            StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);

            for (int i = 0; i < planets.length; i++) {
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
