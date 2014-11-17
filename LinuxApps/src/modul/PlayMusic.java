package modul;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class PlayMusic {
	private Media media;
	private MediaPlayer mediaPlayer;
	private double valueVol;

	public PlayMusic() {
		media = new Media(
				"file:/E:/Video/Music/Let%20It%20Go%20(from%20-Frozen-)%20-%20Demi%20Lovato+%20Official%20music%20video%20cover%20by%20Jannina%20W.mp4");
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
	
	public String pathFile(String path) {
		path = path.replaceAll(" ", "%20");
		StringBuilder sb = new StringBuilder("file:/");
		for (int i = 0; i < path.length(); i++) {
			if ('\\' == path.charAt(i)) {
				sb.append("/");
			} else {
				sb.append(path.charAt(i));
			}
		}
		return sb.toString();
	}

	
	public void playNext(String path) {
		stopVideo();
		media = new Media(path);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		System.out.println(path);
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public Media getMedia() {
		return media;
	}

	public String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
				- elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60
					- durationMinutes * 60;

			if (durationHours > 0) {
				return String.format("%d : %02d : %02d / %d : %02d : %02d",
						elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d : %02d / %02d : %02d",
						elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d : %02d : %02d", elapsedHours,
						elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d : %02d", elapsedMinutes,
						elapsedSeconds);
			}
		}
	}

	public void btPlay(Duration time) {
		if (time == null) {
			mediaPlayer.play();
			return;
		}
		mediaPlayer.seek(time);
		mediaPlayer.play();
	}

	public void stopVideo() {
		mediaPlayer.stop();
	}

	public Duration btPause() {
		Duration time = mediaPlayer.getCurrentTime();
		mediaPlayer.pause();
		return time;
	}

	public void muteVolume() {
		valueVol = mediaPlayer.getVolume();
		mediaPlayer.setVolume(0);
	}
	
	public void deMuteVolume(double valueVol) {
		this.valueVol = valueVol;
		deMuteVolume();
	}
	
	public void deMuteVolume() {
		mediaPlayer.setVolume(valueVol);		
	}

	public boolean isMute() {
		return mediaPlayer.getVolume() == 0;
	}
}
