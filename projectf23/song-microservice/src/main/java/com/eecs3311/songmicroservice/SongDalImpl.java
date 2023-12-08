package com.eecs3311.songmicroservice;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class SongDalImpl implements SongDal {

	private final MongoTemplate db;

	@Autowired
	public SongDalImpl(MongoTemplate mongoTemplate) {
		this.db = mongoTemplate;
		
	}

	@Override
	public DbQueryStatus addSong(Song songToAdd) {
		try {
			db.insert(songToAdd);
			return new DbQueryStatus("New Song Added Successfully", DbQueryExecResult.QUERY_OK );
			
		}catch (Exception e){
			return new DbQueryStatus("Song not added", DbQueryExecResult.QUERY_ERROR_GENERIC);
			
		}
		
	
	}

	@Override
	public DbQueryStatus findSongById(String songId) {
		// TODO Auto-generated method stub
		try {
			
			
			Song foundSong = db.findById(new ObjectId(songId), Song.class);
			
			if (foundSong==null) {
				
				
				DbQueryStatus queryStatus =new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				queryStatus.setData(foundSong);
				
				return queryStatus;
			
			}
			else {
				DbQueryStatus queryStatus =new  DbQueryStatus("Song Found Successfully", DbQueryExecResult.QUERY_OK );
				queryStatus.setData(foundSong);
				return queryStatus;
				
				
			}
			
			
		}catch(Exception e) {
			return new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_GENERIC);
			
			
			
		}
		
	}

	@Override
	public DbQueryStatus getSongTitleById(String songId) {
		// TODO Auto-generated method stub
		
		try {
			
			Song foundSong= db.findById(new ObjectId(songId), Song.class);
		//	String songTitle= foundSong.getSongName();
			
             if (foundSong==null) {
				
            	 DbQueryStatus queryStatus =new DbQueryStatus("Song title not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
            		queryStatus.setData(foundSong);
    				return queryStatus;
			}
			else {
				 DbQueryStatus queryStatus =new DbQueryStatus("Song title Found", DbQueryExecResult.QUERY_OK );
				 queryStatus.setData(foundSong);
				 return queryStatus;
				
				
			}
			
		//	return new DbQueryStatus("Song title Found", DbQueryExecResult.QUERY_OK);
			
		}catch(Exception e) {
			return new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_GENERIC);
			
			
			
			
		}
		
	
		
		//return null;
	}

	@Override
	public DbQueryStatus deleteSongById(String songId) {
		// TODO Auto-generated method stub
		
		
		try {
			
			Song foundSong= db.findById(new ObjectId(songId),Song.class);
			
			
			if (foundSong==null) {
				
				DbQueryStatus queryStatus = new DbQueryStatus("Not Found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				queryStatus.setData(foundSong);
				return queryStatus;
				
				
				
			}else {
			
			DbQueryStatus queryStatus = new DbQueryStatus("Deleted Successfully", DbQueryExecResult.QUERY_OK);
			queryStatus.setData(foundSong);
			db.remove(foundSong);
			return queryStatus;
			}
			
		}catch(Exception e) {
			
            DbQueryStatus queryStatus = new DbQueryStatus("Error", DbQueryExecResult.QUERY_ERROR_GENERIC);
			
			return queryStatus;
			
					
		}
		
	}

	@Override
	public DbQueryStatus updateSongFavouritesCount(String songId, boolean shouldDecrement) {
		// TODO Auto-generated method stub
		
		try {
	
    	Song foundSong= db.findById(new ObjectId(songId), Song.class);
		
		if (foundSong==null) {  
			
			
		
		DbQueryStatus queryStatus=new DbQueryStatus("Song Not Found ", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
		return queryStatus;
		
		}else {
			
			
			if (shouldDecrement) {
				
				
				if (foundSong.getSongAmountFavourites()>0) {
			
					foundSong.setSongAmountFavourites(foundSong.getSongAmountFavourites()-1);
			        db.save(foundSong);
			
			        DbQueryStatus queryStatus=new DbQueryStatus("Song Favourite Count Updated Successfully", DbQueryExecResult.QUERY_OK);
			        return queryStatus;
			
				}
				else {
					
					 DbQueryStatus queryStatus=new DbQueryStatus("Cannot update count<0", DbQueryExecResult.QUERY_ERROR_GENERIC);
				     return queryStatus;
				
				
				}
			}
			
			
			else {
				DbQueryStatus queryStatus=new DbQueryStatus("Should Decrement is false", DbQueryExecResult.QUERY_OK);
				return queryStatus;
				
				
			}
			
			
			
		}
			
			
			
		}catch(Exception e) {
		
			DbQueryStatus queryStatus=new DbQueryStatus("Failed to Update", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
			return queryStatus;
			
		
	
	}
}

	@Override
	public DbQueryStatus updateSongComposerName(String songId, String composerName) {
		
		
		String composername=composerName;
		
		try{
			
			Song foundSong= db.findById(songId, Song.class);
			
			
			if (foundSong==null) {
				
				DbQueryStatus queryStatus= new DbQueryStatus("Song Not Found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				return queryStatus;
			}
			
			else {
				
				
				foundSong.setComposerName(composername);
				db.save(foundSong);
				DbQueryStatus queryStatus =new  DbQueryStatus("Song Composer Name updated Successfully", DbQueryExecResult.QUERY_OK );
				
				return queryStatus;
			
			}
					
			
			
		}catch(Exception e) {
			return new DbQueryStatus( "erorr", DbQueryExecResult.QUERY_ERROR_GENERIC);
			
			
		}
		
		
	
		
	}
	
	
	
////	
////	
////		if (foundSong.getSongAmountFavourites()>0) {
//	
//	foundSong.setSongAmountFavourites(foundSong.getSongAmountFavourites()-1);
//    db.save(foundSong);
//
//    DbQueryStatus queryStatus=new DbQueryStatus("Song Favourite Count Updated Successfully", DbQueryExecResult.QUERY_OK);
//    return queryStatus;
//
//}
//else {
//	
//	 DbQueryStatus queryStatus=new DbQueryStatus("Cannot update count<0", DbQueryExecResult.QUERY_ERROR_GENERIC);
//     return queryStatus;
//
//
//}
//}
//
//
//else {
//DbQueryStatus queryStatus=new DbQueryStatus("Should Decrement is false", DbQueryExecResult.QUERY_OK);
//return queryStatus;
//
//
//}
//
//
//
//}
//
//
//
//}catch(Exception e) {
//
//DbQueryStatus queryStatus=new DbQueryStatus("Failed to Update", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
//return queryStatus;
//
//
//
//}
//}
//
////}
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public DbQueryStatus updateReleaseYear(String songId, Integer releaseYear) {
		
		
		
		
		
		
Integer releaseyear=releaseYear;
		
		try{
			
			Song foundSong= db.findById(songId, Song.class);
			
			
			if (foundSong==null) {
				
				DbQueryStatus queryStatus= new DbQueryStatus("Song Not Found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				return queryStatus;
			}
			
			else {
				
				
				foundSong.setReleaseYear(releaseyear);
				db.save(foundSong);
				DbQueryStatus queryStatus =new  DbQueryStatus("Song Release Year Updated Successfully", DbQueryExecResult.QUERY_OK );
				
				return queryStatus;
			
			}
					
			
			
		}catch(Exception e) {
			return new DbQueryStatus( "erorr", DbQueryExecResult.QUERY_ERROR_GENERIC);
			
			
		}
		
		
	
		
	}
	
	
}