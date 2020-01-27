package ru.zhmyrko.framework;

/**
 * Created by Konstantin on 20.09.2017.
 */

public interface Music {

    public void play();
    public void stop();
    public void setLooping(boolean isLooping);
    public void setVolume(float volume);
    public boolean isPlaying();
    public boolean isStopped();
    public boolean isLooping();
    public void dispose();
}
