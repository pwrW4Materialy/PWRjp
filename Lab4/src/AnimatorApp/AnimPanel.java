package AnimatorApp;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// bufor
	Image image;
	// wykreslacz ekranowy
	Graphics2D device;
	// wykreslacz bufora
	Graphics2D buffer;

	private int delay = 30;

	private Timer timer;

	private static int numer = 0;

	private int width = 0;
	private int height = 0;

	private boolean firstRun = true;
	private boolean animState = true;

    private PublishSubject<Boolean> figuraPublishSubject;

	public AnimPanel() {
		super();
		setBackground(Color.WHITE);
		timer = new Timer(delay, this);
        figuraPublishSubject = PublishSubject.create(); //init PublishSubject
	}

	public void initialize() {
		width = getWidth();
		height = getHeight();

		image = createImage(width, height);
		buffer = (Graphics2D) image.getGraphics();
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		buffer.setBackground(Color.WHITE);
		device = (Graphics2D) getGraphics();
		device.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	void addFig() {
		Figura fig = (numer++ % 2 == 0) ? new Kwadrat(buffer, delay, getWidth(), getHeight())
				: new Elipsa(buffer, delay, getWidth(), getHeight());

		if(!animState){
		    fig.toggleAnim();
        }

		figureSubscribe(fig);

		timer.addActionListener(fig);
		new Thread(fig).start();
	}

	void animate() {
        if(!firstRun){
            figuraPublishSubject.onNext(false); //toogle anim event
        }

		if (timer.isRunning()) {
			timer.stop();
			animState = false;
		} else {
			timer.start();
			firstRun = false;
			animState = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		device.drawImage(image, 0, 0, null);
		buffer.clearRect(0, 0, getWidth(), getHeight());
	}

	void changeSize(int width, int height){
        setSize(width, height);
	    initialize();

	    figuraPublishSubject.onNext(true); //window resided event
	}

	void removeAllFigures(){
        figuraPublishSubject.onComplete();
        figuraPublishSubject = PublishSubject.create();
        initialize();
        animState = true;
    }

	private void figureSubscribe(Figura figura){ //subscribe to PublishSubject
		figuraPublishSubject.subscribeOn(Schedulers.computation()).subscribe(new FiguraObserver(figura));
	}

    private class FiguraObserver implements Observer<Boolean> { //observer class for handling figure events

	    private Figura figura;

	    FiguraObserver(Figura figura) {
            this.figura = figura;
        }

        @Override
        public void onSubscribe(Disposable d) {
	        System.out.println("@" + figura.hashCode() + " Subscribed");
        }

        @Override
        public void onNext(Boolean eventType) {
	        if(eventType) {
                figura.updateBuffer(buffer, width, height);
                System.out.println("@" + figura.hashCode() + " update frame info");
            }
            else{
	            figura.toggleAnim();
                System.out.println("@" + figura.hashCode() + " toogled animation");
            }
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("error, " + e.toString());
        }

        @Override
        public void onComplete() {
            System.out.println("@" + figura.hashCode() + " done");
            timer.removeActionListener(figura);
            figura = null;
        }
    }
}
