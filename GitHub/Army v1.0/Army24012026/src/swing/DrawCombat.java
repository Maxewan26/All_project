package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import entity.Entity;

public class DrawCombat extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sizeEntity = 20;
	private final BufferedImage image;
    private int width;
    private int height;

    public DrawCombat(int width, int height) {
        this.width = width*sizeEntity;
        this.height = height*sizeEntity;

        image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        // Remplissage noir
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                image.setRGB(x, y, 0x000000);
            }
        }

        setPreferredSize(new Dimension(this.width, this.height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void setPixel(int x, int xEnd, int y, int yEnd, int rgb) {
        for (int i = x; i < xEnd; i++) {
            for (int j = y; j < yEnd; j++) {
                image.setRGB(i, j, rgb);
            }
        }
    }

    public void update(Entity[] LeftTeam, Entity[] RightTeam) {
        // Clear
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, 0x000000);
            }
        }

        Entity tempEntity;

        for (int i = 0; i < LeftTeam.length; i++) {
            tempEntity = LeftTeam[i];
            if (tempEntity != null && !tempEntity.getDead()) {
                setPixel(
                    tempEntity.getX()*sizeEntity, tempEntity.getX()*sizeEntity + sizeEntity-1,
                    tempEntity.getY()*sizeEntity, tempEntity.getY()*sizeEntity + sizeEntity-1,
                    tempEntity.getColor()
                );
            }
        }

        for (int i = 0; i < RightTeam.length; i++) {
            tempEntity = RightTeam[i];
            if (tempEntity != null && !tempEntity.getDead()) {
                setPixel(
                    tempEntity.getX()*sizeEntity, tempEntity.getX()*sizeEntity + sizeEntity-1,
                    tempEntity.getY()*sizeEntity, tempEntity.getY()*sizeEntity + sizeEntity-1,
                    tempEntity.getColor()
                );
            }
        }

        repaint(); // demande le rafraîchissement de l’écran
    }
}
