package gui.controllers;

import gui.GameScene;

import java.net.URL;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.AvoidLiteralsInIfCondition"})
public class Homepage extends Controller implements Initializable {


    @FXML
    public TextField nickname;

    @FXML
    AnchorPane anchor;

    @FXML
    Pane drop;
    @FXML
    Pane top;
    @FXML
    Pane mid;
    @FXML
    Pane bot;

    @FXML
    Pane controls;
    @FXML
    Pane vol;
    @FXML
    Pane quit;

    @FXML
    Label controlsDisplay;

    public static boolean click;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawDropMenu(anchor, controls, vol, quit);

        controlsDisplay.setText("W: Accelerate\nS: Decelerate\n"
                + "A: Steer left\nD: Steer left\nSpacebar: Shoot lasers\nP: Pause");
        controlsDisplay.setOpacity(0.0);
    }

    @FXML
    void lightUp(MouseEvent event) {

        Button btn = (Button) event.getSource();
        lightBtn(btn);
    }

    @FXML
    void darken(MouseEvent event) {

        Button btn = (Button) event.getSource();
        darkenBtn(btn);

    }

    @FXML
    void combine(MouseEvent event) {
        Timeline tm = animate(false, mid, controls, bot, vol, quit);
        tm.play();
    }

    @FXML
    void displayControls(MouseEvent event) {
        display(controlsDisplay);
    }

    @FXML
    void fix(MouseEvent event) {
        Timeline tm = animate(true, mid, controls, bot, vol, quit);
        tm.play();
    }

    @FXML
    void mute(MouseEvent event) {
        muteSound(vol);
    }

    /**
     * opens the game when the user presses start.
     * @param mouseEvent the user's click.
     */
    public void clickStart(MouseEvent mouseEvent) {
        click = true;
        setNickname();
        Platform.exit();
    }

    /**
     * displays high scores when user clicks button.
     * @param mouseEvent users click.
     * @throws SQLException database query exception.
     */
    public void clickHighScore(MouseEvent mouseEvent) throws SQLException {
        HighScoreBoard.getBoard();
    }

    public TextField getNickname() {
        return nickname;
    }

    public void setNickname() {
        GameScene.nickname = this.nickname.getText();
    }
}
