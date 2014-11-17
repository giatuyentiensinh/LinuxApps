package control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import main.Main;
import modul.PlayMusic;

public class Controller implements Initializable {

	@FXML
	private MediaView mv;
	@FXML
	private BorderPane root;
	@FXML
	private AnchorPane containControl;
	@FXML
	private MenuBar mnBar;
	@FXML
	private Button btFullScreen;
	@FXML
	private Slider slTime;
	@FXML
	private Slider slVolume;
	@FXML
	private Label lbTime;
	@FXML
	private Button btPlay;
	@FXML
	private ImageView ivSound;
	@FXML
	private MenuItem miMute;
	private ChangeListener<Duration> t;
	private boolean checkTime = false;
	private PlayMusic pm = new PlayMusic();

	private Point2D pre;
	private Point2D screen;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mv.setMediaPlayer(pm.getMediaPlayer());
		moveScreen();
		init();
	}

	private void moveScreen() {

		mnBar.setOnMousePressed((MouseEvent e) -> {
			screen = new Point2D(e.getScreenX(), e.getScreenY());
		});

		mnBar.setOnMouseDragged((MouseEvent e) -> {
			if (screen != null && pre != null) {
				Main.stage.setX(e.getScreenX() + pre.getX() - screen.getX());
				Main.stage.setY(e.getScreenY() + pre.getY() - screen.getY());
			}
		});

		mnBar.setOnMouseReleased((MouseEvent e) -> {
			pre = new Point2D(Main.stage.getX(), Main.stage.getY());
		});
	}

	private void init() {
		Main.stage.fullScreenProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> ov,
							Boolean oVal, Boolean nVal) {
						containControl.setVisible(!nVal);
					}
				});
		pm.getMediaPlayer()
				.setOnReady(
						() -> {
							slTime.setMax(pm.getMedia().getDuration()
									.toMillis() / 1000);
							t = (ov, oVal, nVal) -> {
								slTime.setValue(nVal.toSeconds());
								lbTime.setText(pm.formatTime(nVal, pm
										.getMedia().getDuration()));
							};
							pm.getMediaPlayer().currentTimeProperty()
									.addListener(t);
							slVolume.valueProperty()
									.addListener(
											(ChangeListener<Number>) (ov, oVal,
													nVal) -> {
												pm.getMediaPlayer()
														.setVolume(
																nVal.doubleValue() / 100.0);
												ivSound.setImage(new Image(
														getClass()
																.getResource(
																		"/image/sound.png")
																.toString()));
											});
						});
	}

	@FXML
	public void checkVolume() {
		if (pm.isMute()) {
			ivSound.setImage(new Image(getClass().getResource(
					"/image/sound.png").toString()));
			pm.deMuteVolume();
		} else {
			ivSound.setImage(new Image(getClass()
					.getResource("/image/mute.png").toString()));
			pm.muteVolume();
		}
	}

	@FXML
	public void playContinue() {
		Duration time = null;
		if (!checkTime) {
			btPlay.setText("Play");
			time = pm.btPause();
		} else {
			btPlay.setText("Pause");
			pm.btPlay(time);
		}
		checkTime = !checkTime;
	}

	@FXML
	public void setTime() {
		Duration time = Duration.seconds(slTime.getValue());
		pm.btPlay(time);
	}

	@FXML
	public void slideDagger() {
		pm.btPause();
	}

	@FXML
	public void stopVideo() {
		pm.stopVideo();
		btPlay.setText("Play");
		checkTime = !checkTime;
	}

	@FXML
	private void exitApps() {
		Platform.exit();
	}

	@FXML
	private void btFullScreen() {
		Main.stage.setFullScreen(true);
		mv.fitHeightProperty().bind(Main.stage.heightProperty());
		mv.fitWidthProperty().bind(Main.stage.widthProperty());
	}

	@FXML
	private void openFile() {
		FileChooser fchose = new FileChooser();
		fchose.setTitle("Open file");
		fchose.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All file", "*.*"),
				new FileChooser.ExtensionFilter("MP3", "*.mp3"),
				new FileChooser.ExtensionFilter("MP4", "*.mp4"));
		File file = fchose.showOpenDialog(null);
		if (file == null) {
			return;
		}
		pm.playNext(pm.pathFile(file.getPath()));
		mv.setMediaPlayer(pm.getMediaPlayer());
		init();
	}

	
}
