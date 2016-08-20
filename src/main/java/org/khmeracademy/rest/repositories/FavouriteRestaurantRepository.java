package org.khmeracademy.rest.repositories;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.khmeracademy.rest.entities.FavouriteRestaurants;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRestaurantRepository {
	/* =================  INSERT DATA   =================== */
	String C_FREST = "INSERT INTO favouriterestaurants (user_id, rest_id)"
			+ "VALUES(#{user.user_id}, #{rest.rest_id})";
	@Insert(C_FREST)
	@Results(value={
		@Result(property="user.user_id", column="user_id"),
		@Result(property="rest.rest_id", column="rest_id")
	})
	public boolean insertFavouriteRestaurant(FavouriteRestaurants favouriteRestaurant);
	
	/* =================  GET DATA   =================== */
	String R_FREST = "SELECT F.favrest_id,"
			+ " R.rest_name,"
			+ " R.rest_name_kh, "
			+ " U.user_id,"
			+ " U.first_name,"
			+ " U.last_name"
			+ " From favouriterestaurants F"
			+ " INNER JOIN restaurants R"
			+ " ON F.rest_id=R.rest_id"
			+ " INNER JOIN users U "
			+ " ON U.user_id = F.user_id;"
			+ " SELECT COUNT(F.rest_id) As total,"
			+ " U.user_id"
			+ " From favouriterestaurants F"
			+ " INNER JOIN users U"
			+ " ON F.user_id = U.user_id"
			+ " GROUP BY U.user_id";
	@Select(R_FREST)
	@Results(value={
		@Result(property="user.user_id", column="user_id"),
		@Result(property="user.first_name", column="first_name"),
		@Result(property="user.last_name", column="last_name"),
		@Result(property="rest.rest_id", column="rest_id"),
		@Result(property="rest.rest_name", column="rest_name"),
		@Result(property="fav_total", column="total")
		
		
		
	})
	public ArrayList<FavouriteRestaurants> getFavouriteRestaurant();
	
	/* =================  GET DATA  BY ID =================== */
	String F_FREST = "SELECT favrest_id, user_id, rest_id FROM favouriterestaurants WHERE favrest_id = #{favrest_id}";
	@Select(F_FREST)
	@Results(value={
		@Result(property="user.user_id", column="user_id"),
		@Result(property="rest.rest_id", column="rest_id")
	})
	public FavouriteRestaurants findFavouriteRestaurantById(@Param("favrest_id") int favrest_id);
	
	/* =================  UPDATE DATA   =================== */
	String U_FREST = "UPDATE favouriterestaurants set user_id=#{user.user_id},"
			+ "rest_id=#{rest.rest_id} WHERE favrest_id = #{favrest_id}";
	@Update(U_FREST)
	@Results(value={
		@Result(property="user.user_id", column="user_id"),
		@Result(property="rest.rest_id", column="rest_id")
	})
	public boolean updateFavouriteRestaurant(FavouriteRestaurants favouriteRestaurant);
	
	/* =================  DELETE DATA   =================== */
	String D_FREST = "DELETE FROM favouriterestaurants WHERE favrest_id = #{favrest_id}";
	@Delete(D_FREST)
	public boolean deleteFavouriteRestaurant(@Param("favrest_id") int favrest_id);
	
	/*================== GET DATA BY USER ID======================================*/
	/*String G_FREST="SELECT"
			+ " R.rest_name,"
			+ " R.rest_name_kh, "
			+ " U.user_id,"
			+ " U.first_name,"
			+ " U.last_name"
			+ " From favouriterestaurants F"
			+ " INNER JOIN restaurants R"
			+ " ON F.rest_id=R.rest_id"
			+ " INNER JOIN users U "
			+ " ON U.user_id = F.user_id"
			+ " WHERE U.user_id=#{user_id};"
			+ " SELECT COUNT(F.rest_id) As total"
			+ " From favouriterestaurants F "
			+ " Where F.user_id=#{user_id}";
	
	@Select(G_FREST)
	@Results(value={
			@Result(property="user.user_id", column="user_id"),
			@Result(property="user.first_name", column="first_name"),
			@Result(property="user.last_name", column="last_name"),
			@Result(property="rest.rest_id", column="rest_id"),
			@Result(property="rest.rest_name", column="rest_name"),
			@Result(property="fav_total", column="total")	
		})*/

	/*public ArrayList<FavouriteRestaurants> getFavouriteRestaurantByUserId(@Param("user_id") int user_id);*/
	
	String G_FREST="SELECT"
		+ " R.rest_name,"	
		+ " R.rest_name_kh, "
		+ " U.user_id,"
		+ " U.first_name,"
		+ " U.last_name"
		+ " From favouriterestaurants F"
		+ " INNER JOIN restaurants R"
		+ " ON F.rest_id=R.rest_id"
		+ " INNER JOIN users U "
		+ " ON U.user_id = F.user_id"
		+ " WHERE U.user_id=#{user_id}";
	@Results(value={
			@Result(property="user.user_id", column="user_id"),
			@Result(property="user.first_name", column="first_name"),
			@Result(property="user.last_name", column="last_name"),
			@Result(property="rest.rest_id", column="rest_id"),
			@Result(property="rest.rest_name", column="rest_name"),
			// fav_total got data from totalFavourite 
			// column user_id send parameter to totalFavourite(int user_id);
			@Result(property ="fav_total", column="user_id", many=@Many(select="totalFavourite"))
	})
	@Select(G_FREST)
	public ArrayList<FavouriteRestaurants> getFavouriteRestaurantByUserId(@Param("user_id") int user_id);
	
	String C_USER_FAV = "SELECT COUNT(F.rest_id) As favtotal "
			+ " From favouriterestaurants F "
			+ " Where F.user_id= #{user_id} ";				 
	@Select(C_USER_FAV)
	public int totalFavourite(int user_id);
	
	
	
	
}