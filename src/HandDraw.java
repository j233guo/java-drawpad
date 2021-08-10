import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class HandDraw {
    private Frame frame = new Frame("Simple Draw Pad");

    private final int AREA_WIDTH = 500;
    private final int AREA_HEIGHT = 400;

    // Make a popup menu, to select brush color
    private PopupMenu colorMenu = new PopupMenu();
    private MenuItem redItem = new MenuItem("Red");
    private MenuItem greenItem = new MenuItem("Green");
    private MenuItem blueItem = new MenuItem("Blue");

    // Determine the color
    private Color forceColor = Color.BLACK;

    // Create buffered image and get graphics
    BufferedImage image = new BufferedImage(AREA_WIDTH, AREA_HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();

    // Custom canvas
    private class MyCanvas extends Canvas {
        @Override
        public void paint(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }

    MyCanvas drawArea = new MyCanvas();

    // Determine the last position of mouse cursor
    private int preX = -1;
    private int preY = -1;

    public void init() {
        // Construct the view and control logic

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                switch (actionCommand) {
                    case "Red":
                        forceColor = Color.RED;
                        break;
                    case "Green":
                        forceColor = Color.GREEN;
                        break;
                    case "Blue":
                        forceColor = Color.BLUE;
                        break;
                }
            }
        };

        redItem.addActionListener(listener);
        greenItem.addActionListener(listener);
        blueItem.addActionListener(listener);

        colorMenu.add(redItem);
        colorMenu.add(greenItem);
        colorMenu.add(blueItem);

        // Set color menu to draw area
        drawArea.add(colorMenu);

        drawArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {// Called when mouse released
                boolean popupTrigger = e.isPopupTrigger();
                if (popupTrigger) {
                    colorMenu.show(drawArea, e.getX(), e.getY());
                }
                // Set mouse position
                preX = -1;
                preY = -1;
            }
        });

        // Set the background as white
        g.setColor(Color.white);
        g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);

        // Implement drawing by listening to mouse motions
        drawArea.addMouseMotionListener(new MouseMotionAdapter() {
            //该方法，当鼠标左键按下，并进行拖动时，会被调用
            @Override
            public void mouseDragged(MouseEvent e) {
                if (preX > 0 && preY > 0) {
                    g.setColor(forceColor);
                    // Draw lines, with position x and y
                    g.drawLine(preX, preY, e.getX(), e.getY());
                }

                // Update x and y-axis and repaint
                preX = e.getX();
                preY = e.getY();
                drawArea.repaint();
            }
        });

        drawArea.setPreferredSize(new Dimension(AREA_WIDTH,AREA_HEIGHT));
        frame.add(drawArea);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new HandDraw().init();
    }
}
