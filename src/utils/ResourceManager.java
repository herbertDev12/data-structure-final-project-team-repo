package utils;

import java.awt.image.BufferedImage;

public class ResourceManager {
    public static BufferedImage player;//imagen del jugador
    //jugador diablo
    public static BufferedImage devil1D;
    public static BufferedImage devil2D;
    public static BufferedImage devil3D;
    public static BufferedImage devil1I;
    public static BufferedImage devil2I;
    public static BufferedImage devil3I;

    //slime
    public static BufferedImage slimeI;
    public static BufferedImage slimeD;
    public static BufferedImage slime;
    //esqueleto
    public static BufferedImage esq1;
    public static BufferedImage esq2;
    public static BufferedImage esq3;

    //Goblin
    public static BufferedImage gob1;
    public static BufferedImage gob2;
    public static BufferedImage gob3;

    //murcielago
    public static BufferedImage bat1;
    public static BufferedImage bat2;
    public static BufferedImage bat3;

    //cristales
    public static BufferedImage c1;
    public static BufferedImage c2;
    public static BufferedImage c3;
    public static BufferedImage c4;
    public static BufferedImage c5;
    //llave
    public static BufferedImage key;
    //puertafinal
    public static BufferedImage finaldoor;
    //portal
    public static BufferedImage p1;
    public static BufferedImage p2;
    public static BufferedImage p3;
    public static BufferedImage p4;
    public static BufferedImage p5;
    //suelos
    public static BufferedImage floor;
    //parded
    public static BufferedImage wall;

    public static void init(){
        player= Loader.CargadorImagenes("/Assets/img.png");
        slimeI=Loader.CargadorImagenes("/Assets/slimeI.png");
        slimeD=Loader.CargadorImagenes("/Assets/slimeD.png");
        slime=Loader.CargadorImagenes("/Assets/slime.png");
        c1=Loader.CargadorImagenes("/Assets/c1.png");
        c2=Loader.CargadorImagenes("/Assets/c2.png");
        c3=Loader.CargadorImagenes("/Assets/c3.png");
        c4=Loader.CargadorImagenes("/Assets/c4.png");
        c5=Loader.CargadorImagenes("/Assets/c5.png");
        key=Loader.CargadorImagenes("/Assets/key.png");
        p1=Loader.CargadorImagenes("/Assets/p1.png");
        p2=Loader.CargadorImagenes("/Assets/p2.png");
        p3=Loader.CargadorImagenes("/Assets/p3.png");
        p4=Loader.CargadorImagenes("/Assets/p4.png");
        p5=Loader.CargadorImagenes("/Assets/p5.png");
        floor=Loader.CargadorImagenes("/Assets/floor.png");
        wall=Loader.CargadorImagenes("/Assets/wall1.png");
        finaldoor=Loader.CargadorImagenes("/Assets/finaldoor.png");
        esq1=Loader.CargadorImagenes("/Assets/esq1.png");
        esq2=Loader.CargadorImagenes("/Assets/esq2.png");
        esq3=Loader.CargadorImagenes("/Assets/esq3.png");
        gob1=Loader.CargadorImagenes("/Assets/gob1.png");
        gob2=Loader.CargadorImagenes("/Assets/gob2.png");
        gob3=Loader.CargadorImagenes("/Assets/gob3.png");
        devil1D=Loader.CargadorImagenes("/Assets/devil1D.png");
        devil2D=Loader.CargadorImagenes("/Assets/devil2D.png");
        devil3D=Loader.CargadorImagenes("/Assets/devil3D.png");
        devil1I=Loader.CargadorImagenes("/Assets/devil1I.png");
        devil2I=Loader.CargadorImagenes("/Assets/devil2I.png");
        devil3I=Loader.CargadorImagenes("/Assets/devil3I.png");
        bat1=Loader.CargadorImagenes("/Assets/bat1.png");
        bat2=Loader.CargadorImagenes("/Assets/bat2.png");
        bat3=Loader.CargadorImagenes("/Assets/bat3.png");


    }
}
