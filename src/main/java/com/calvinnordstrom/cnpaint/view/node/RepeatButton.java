package com.calvinnordstrom.cnpaint.view.node;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * A {@link RepeatButton} is a {@link javafx.scene.control.Button} that will
 * continuously handle events while the user is pressing it.
 * <p>
 * The {@link RepeatButton} will handle one event when the user initially
 * presses it. After a specified delay, the button will continuously handle
 * events with a specified duration. The duration is the time between events.
 *
 * @author Calvin Nordstrom
 */
public class RepeatButton extends DefaultButton {
    private int duration;
    private int delay;
    private EventHandler<ActionEvent> onFinished;
    private Timeline timeline = new Timeline();
    private PauseTransition pauseTransition;

    /**
     * Constructs a default {@link RepeatButton} with a 500-millisecond delay
     * and a 50-millisecond duration.
     */
    public RepeatButton() {
        this(500, 50, null);
    }

    /**
     * Constructs a {@link RepeatButton} with the specified delay, duration,
     * and event handler.
     * <p>
     * The delay and duration are assumed to be in milliseconds.
     *
     * @param delay the delay in milliseconds
     * @param duration the duration in milliseconds
     * @param onFinished the on finished event handler
     */
    public RepeatButton(int delay, int duration, EventHandler<ActionEvent> onFinished) {
        super();
        this.delay = delay;
        this.duration = duration;
        this.onFinished = onFinished;

        refresh();
        init();
    }

    private void init() {
        setOnMousePressed(_ -> {
            timeline.setCycleCount(1);
            timeline.play();
            pauseTransition.setOnFinished(_ -> {
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
            });
            pauseTransition.play();
        });
        setOnMouseReleased(_ -> {
            timeline.stop();
            pauseTransition.stop();
        });
        setOnMouseExited(_ -> {
            timeline.stop();
            pauseTransition.stop();
        });
    }

    /**
     * Sets the delay before repeating in milliseconds to the specified delay.
     *
     * @param delay the delay in milliseconds
     */
    public void setDelay(int delay) {
        this.delay = delay;
        refresh();
    }

    /**
     * Sets the duration between repeated actions in milliseconds to the
     * specified duration.
     *
     * @param duration the duration in milliseconds
     */
    public void setDuration(int duration) {
        this.duration = duration;
        refresh();
    }

    /**
     * Sets the on finished event handler to the specified event handler.
     *
     * @param onFinished the on finished event handler
     */
    public void setOnFinished(EventHandler<ActionEvent> onFinished) {
        this.onFinished = onFinished;
        refresh();
    }

    private void refresh() {
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), onFinished);
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        pauseTransition = new PauseTransition(Duration.millis(delay));
    }
}
