package com.eecs3311.profilemicroservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import org.springframework.stereotype.Repository;

//import com.eecs3311.songmicroservice.DbQueryStatus;
//import com.eecs3311.song

import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Values;

@Repository
public class ProfileDriverImpl implements ProfileDriver {

	Driver driver = ProfileMicroserviceApplication.driver;

	public static void InitProfileDb() {
		String queryStr;

		try (Session session = ProfileMicroserviceApplication.driver.session()) {
			try (Transaction trans = session.beginTransaction()) {
				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT exists(nProfile.userName)";
				trans.run(queryStr);

				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT exists(nProfile.password)";
				trans.run(queryStr);

				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT nProfile.userName IS UNIQUE";
				trans.run(queryStr);

				trans.success();
			} catch (Exception e) {
				if (e.getMessage().contains("An equivalent constraint already exists")) {
					System.out.println("INFO: Profile constraints already exist (DB likely already initialized), should be OK to continue");
				} else {
					// something else, yuck, bye
					throw e;
				}
			}
			session.close();
		}
	}
	
	@Override
	public DbQueryStatus createUserProfile(String userName, String fullName, String password) {
		
		
	    
	    try (Session session = driver.session()) {
	        String query = "MERGE (p:profile {userName: $userName, fullName: $fullName, password: $password}) RETURN p";
	        StatementResult result = session.run(query, Values.parameters("userName", userName, "fullName", fullName, "password", password));
	        
	        if (result.hasNext()) {
	        	
	        	DbQueryStatus queryStatus =new DbQueryStatus("Profile created or already exists",DbQueryExecResult.QUERY_OK);
	        	 return queryStatus;
	           
	        } else {
	        	
	        	DbQueryStatus queryStatus =new DbQueryStatus("No profile was created",DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
	        	 return queryStatus;
	        	
	           }
	    } catch (Exception e) {
	       
	        DbQueryStatus queryStatus =new DbQueryStatus( e.getMessage() ,DbQueryExecResult.QUERY_ERROR_GENERIC);
	        return queryStatus;
	    }
	    
	 
		
		
		
	}
	
	
	
	
	//"MATCH (user:profile {userName: $userName}), (friend:profile {userName: $friendUserName}) " +
//    "MERGE (user)-[:follows]->(friend) " +
//    "RETURN user, friend";

	@Override
	public DbQueryStatus followFriend(String userName, String frndUserName) {
		
//		 
	    try (Session session = driver.session()) {
	        String query = "MATCH (user:profile {userName: $userName}), (friend:profile {userName: $friendUserName}) MERGE(user)-[r:follows]->(friend) RETURN user,friend, type(r)";
	        
	        StatementResult result = session.run(query, Values.parameters("userName", userName, "friendUserName", frndUserName));
	        
	        if (result.hasNext()) {
	        	
	        	DbQueryStatus queryStatus =new DbQueryStatus("User Follows Friend Successfully",DbQueryExecResult.QUERY_OK);
	        	 return queryStatus;
	           
	        } else {
	        	
	        	DbQueryStatus queryStatus =new DbQueryStatus("Follow Unsuccessfull",DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
	        	 return queryStatus;
	        	
	           }
	    } catch (Exception e) {
	       
	        DbQueryStatus queryStatus =new DbQueryStatus( e.getMessage() ,DbQueryExecResult.QUERY_ERROR_GENERIC);
	        return queryStatus;
	    }
	    
		
	}
		



	@Override
	public DbQueryStatus unfollowFriend(String userName, String frndUserName) {
		
		
		try(Session session= driver.session()){
			String query="MATCH(user:profile {username: $userName})-[r:follows]->(friend:profile {userName: $friendUserName}) DELETE r RETURN user, friend";
			
			StatementResult result= session.run(query, Values.parameters("userName", userName, "friendUserName", frndUserName));
			
			
			if (result.hasNext()) {
				
				DbQueryStatus queryStatus= new DbQueryStatus("Unfollowed Succesfully", DbQueryExecResult.QUERY_OK );
				return queryStatus;
				
			}
			
			else {
				DbQueryStatus queryStatus =new DbQueryStatus(" unfollow Unsuccessfull",DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
	        	 return queryStatus;
			}
			
			
			
		}catch(Exception e) {
			DbQueryStatus queryStatus =new DbQueryStatus(e.getMessage() , DbQueryExecResult.QUERY_ERROR_GENERIC);
       	   return queryStatus;
			
			
		}
		
	}

	@Override
	public DbQueryStatus getAllSongFriendsLike(String userName) {
			
		return null;
	}
}
