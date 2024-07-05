$(document).ready(function() {
    var video = $('#video')[0];
    var playPauseBtn = $('#playPauseBtn');
    var rewindBtn = $('#rewindBtn');
    var forwardBtn = $('#forwardBtn');
    var seekBar = $('#seekBar');
    var muteBtn = $('#muteBtn');
    var volumeBar = $('#volumeBar');
    var fullScreenBtn = $('#fullScreenBtn');
    var speedBtn = $('#speedBtn');
    var timeDisplay = $('#timeDisplay');
    var playbackRate = 1;
    var previousVolume = 1; // 이전 볼륨 값을 저장하는 변수

    function updateDisplayTime() {
        var currentMinutes = Math.floor(video.currentTime / 60);
        var currentSeconds = Math.floor(video.currentTime % 60).toString().padStart(2, '0');
        var durationMinutes = Math.floor(video.duration / 60);
        var durationSeconds = Math.floor(video.duration % 60).toString().padStart(2, '0');
        timeDisplay.text(`${currentMinutes}:${currentSeconds} / ${durationMinutes}:${durationSeconds}`);
    }

    function updateVolumeIcon(volume) {
        var icon;
        if (volume === 0) {
            icon = 'fa-volume-xmark';
        } else if (volume <= 0.33) {
            icon = 'fa-volume-off';
        } else if (volume <= 0.66) {
            icon = 'fa-volume-low';
        } else {
            icon = 'fa-volume-high';
        }
        muteBtn.html(`<i class="fa-solid ${icon}"></i>`);
    }

    function togglePlayPause() {
        if (video.paused) {
            video.play();
            playPauseBtn.html('<i class="fa-solid fa-pause"></i>');
        } else {
            video.pause();
            playPauseBtn.html('<i class="fa-solid fa-play"></i>');
        }
    }

    function toggleMute() {
        if (video.muted) {
            video.muted = false;
            video.volume = previousVolume;
            volumeBar.val(previousVolume);
        } else {
            previousVolume = video.volume;
            video.muted = true;
            video.volume = 0;
            volumeBar.val(0);
        }
        updateVolumeIcon(video.volume);
    }

    $(video).on('loadedmetadata timeupdate', function() {
        var value = (100 / video.duration) * video.currentTime;
        seekBar.val(value);
        updateDisplayTime();
    });

    playPauseBtn.click(togglePlayPause);
    rewindBtn.click(() => video.currentTime = Math.max(0, video.currentTime - 5));
    forwardBtn.click(() => video.currentTime = Math.min(video.duration, video.currentTime + 5));

    seekBar.on('input', function() {
        video.currentTime = video.duration * (seekBar.val() / 100);
    });

    muteBtn.click(toggleMute);

    volumeBar.on('input', function() {
        var volume = parseFloat(volumeBar.val());
        video.volume = volume;
        video.muted = (volume === 0);
        updateVolumeIcon(volume);
    });

    fullScreenBtn.click(function() {
        if (video.requestFullscreen) {
            video.requestFullscreen();
        } else if (video.mozRequestFullScreen) {
            video.mozRequestFullScreen();
        } else if (video.webkitRequestFullscreen) {
            video.webkitRequestFullscreen();
        } else if (video.msRequestFullscreen) {
            video.msRequestFullscreen();
        }
    });

    speedBtn.click(function() {
        playbackRate = playbackRate === 1 ? 1.5 : playbackRate === 1.5 ? 2 : 1;
        video.playbackRate = playbackRate;
        speedBtn.text('x' + playbackRate);
    });

    // 비디오가 로드될 때 크기 조정
    $(video).on('loadedmetadata', function() {
        video.width = 800;
        video.height = 500;
    });

    // Autoplay the video
    video.play();
});
