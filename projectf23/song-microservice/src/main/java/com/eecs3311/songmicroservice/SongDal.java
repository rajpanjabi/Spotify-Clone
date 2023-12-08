package com.eecs3311.songmicroservice;

public interface SongDal {
	DbQueryStatus addSong(Song songToAdd);
	
	
	DbQueryStatus findSongById(String songId);
	
	
	DbQueryStatus getSongTitleById(String songId);
	
	
	DbQueryStatus deleteSongById(String songId);	
	
	
	
	DbQueryStatus updateSongFavouritesCount(String songId, boolean shouldDecrement);
	
	DbQueryStatus updateSongComposerName(String songId, String composerName);
	
	DbQueryStatus updateReleaseYear(String songId, Integer releaseYear);
	
	
	
}
