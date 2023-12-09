package com.eecs3311.profilemicroservice;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Values;

@Repository
public class PlaylistDriverImpl implements PlaylistDriver {

	Driver driver = ProfileMicroserviceApplication.driver;

	public static void InitPlaylistDb() {
		String queryStr;

		try (Session session = ProfileMicroserviceApplication.driver.session()) {
			try (Transaction trans = session.beginTransaction()) {
				queryStr = "CREATE CONSTRAINT ON (nPlaylist:playlist) ASSERT exists(nPlaylist.plName)";
				trans.run(queryStr);
				trans.success();
			} catch (Exception e) {
				if (e.getMessage().contains("An equivalent constraint already exists")) {
					System.out.println("INFO: Playlist constraint already exist (DB likely already initialized), should be OK to continue");
				} else {
					// something else, yuck, bye
					throw e;
				}
			}
			session.close();
		}
	}
	
	
//	MATCH(user:profile {username: $userName})-[r:follows]->(friend:profile {userName: $friendUserName}) DELETE r RETURN user, friend";
//	MATCH (m: movie { title: "CSCC01: The Movie"})
//	SET m.tagline = "Scalability just got a whole lot spicier"
//	RETURN m;
	
	
	@Override
	public DbQueryStatus likeSong(String userName, String songId) {
		
	    try (Session session = driver.session()) {
	        // Check if the user's favorites playlist exists
	        String playlistQuery = "MATCH (user:profile {userName: $userName})-[:created]->(play:playlist {plName: $pname}) CREATE (play)-[:includes]->(song:song {songId: $songId})";
	        
	        StatementResult playlistResult = session.run(playlistQuery, Values.parameters("userName", userName, "pname", userName + "-favorites", "songId", songId));
	       	
	        
	        
	        return new DbQueryStatus("Song liked successfully", DbQueryExecResult.QUERY_OK);
	        
	    } catch (Exception e) {
	        return new DbQueryStatus("Error: " + e.getMessage(), DbQueryExecResult.QUERY_ERROR_GENERIC);
//	    }
//	}
	        
	    }}
//            
		
//		 }
//	return null;
//	}
//		

	@Override
	public DbQueryStatus unlikeSong(String userName, String songId) {
		
		
		  try (Session session = driver.session()) {
		        // Check if the user's favorites playlist exists
		        String playlistQuery = "MATCH (play:playlist {plName: $pname}) -[r:includes]->(songss:song {songId :$songId}) DELETE r ";
		        
		        StatementResult playlistResult = session.run(playlistQuery, Values.parameters("userName", userName, "pname", userName + "-favorites", "songId", songId));
		       	
		        
		        
		        return new DbQueryStatus("Song Deleted successfully", DbQueryExecResult.QUERY_OK);
		        
		    } catch (Exception e) {
		        return new DbQueryStatus("Error: " + e.getMessage(), DbQueryExecResult.QUERY_ERROR_GENERIC);
//		    }
//		}
		        
		    }}
		
		
}
