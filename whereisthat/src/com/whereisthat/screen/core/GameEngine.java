package com.whereisthat.screen.core;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Unit;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.whereisthat.R;
import com.whereisthat.data.Location;
import com.whereisthat.data.Locations;
import com.whereisthat.dialog.IScoreDialogListener;
import com.whereisthat.dialog.IStartDialogListener;
import com.whereisthat.dialog.ScoreDialog;
import com.whereisthat.dialog.StartDialog;
import com.whereisthat.game.rules.GameScore;
import com.whereisthat.game.rules.Round;
import com.whereisthat.helper.GameConstants;
import com.whereisthat.helper.SoundType;
import com.whereisthat.screen.activity.GameTiming;

public class GameEngine {

	private Context context;
	private Resources resources;
	private MapView map;
	private GraphicsLayer locationsLayer;
	private PanelManager panelManager;
	private ProgressDialog progressDialog;
	private Locations locations;
	private Location currentLocation;	
	private GameScore gameScore;
	private GameTiming gameTiming;
	
	public GameEngine(Context context,
				      Resources resorces,
					  MapView map, 					  
			          ProgressBar progressBar, 
			          TextView locationView,
			          TextView gameScoreView,
			          TextView levelView,
			          TextView minimumScoreToAvanceView){
		
		this.context = context;
		this.resources = resorces;
		this.map = map;
		
		gameScore = new GameScore();
		locations = new Locations();
		gameTiming = new GameTiming();		
		panelManager = new PanelManager(gameScoreView, levelView, minimumScoreToAvanceView, locationView, progressBar);	
	}
		
	public void start()
	{
		SoundManager.start(SoundType.inGame);
		progressDialog = ProgressDialog.show(context, "", GameConstants.PROGRESS_DIALOG_MESSAGE);
		locations.loadFromXml(resources);
		initMap();
	}
	
	public void pause() {
		map.pause();
		SoundManager.stop(SoundType.inGame);
	}
	
	public void finish(){
		gameTiming.stopRound();
	}
	
	private void initMap(){
		ArcGISTiledMapServiceLayer baseMap = new ArcGISTiledMapServiceLayer(GameConstants.ARCGIS_MAP_SERVICE_URL);		
		map.addLayer(baseMap);		
		locationsLayer = new GraphicsLayer(map.getSpatialReference(),
				new Envelope(-19332033.11, -3516.27, -1720941.80, 11737211.28));		
		map.addLayer(locationsLayer);			
		
		map.setOnStatusChangedListener(new OnStatusChangedListener() {
			private static final long serialVersionUID = 1L;
			public void onStatusChanged(Object source, STATUS status) {
				if (source == map && status == STATUS.INITIALIZED) {
					gameStart();
				}
			}
		});

		map.setOnSingleTapListener(new OnSingleTapListener() {
			private static final long serialVersionUID = 1L;
			public void onSingleTap(float x, float y) {
				executeSingleTap(x, y);
			}
		});
	}
		
	private void gameStart(){
		progressDialog.dismiss();			
		StartDialog dialog = new StartDialog(context);
		dialog.addListener(new IStartDialogListener() {					
			public void startGame() {
				nextGameRound();
			}
		});		
		dialog.show();		
	}
	
	private void nextGameRound()
	{
		clearLocationsLayer();
		setTargetLocation();
		startRoundTimer();
	}
	
	private void clearLocationsLayer(){
		locationsLayer.removeAll();
	}
	
	private void setTargetLocation(){
		currentLocation = locations.getRandomCity(map);		
		panelManager.setLocationView(currentLocation.toString());	
	}
	
	private void startRoundTimer() {
		gameTiming.stopRound();
		gameTiming.startRound(new Runnable() {
			  public void run() {
				     try {	
				    	 panelManager.setProgress((int) gameTiming.getElapsetTime());
				     } catch (Throwable t) {  }}
			   });
	}

	private void executeSingleTap(float x, float y)	{
		if (!map.isLoaded()) return;
		
		long elapsedTime = gameTiming.getElapsetTime();
		gameTiming.stopRound();
		Point pointClicked = map.toMapPoint(new Point(x, y));
		Point targetPoint = currentLocation.getMapPoint();					
		showFlagPointInMap(pointClicked, R.drawable.flag_clicked);
		showFlagPointInMap(targetPoint, R.drawable.flag_target);		
		double distanceKm = getKmDistanceFromTarget(pointClicked);		
		gameScore.addRound(new Round(distanceKm, elapsedTime));
		long score = gameScore.getScore();
		long roundScore = gameScore.getLastRoundScore();		
		showRoundScore(distanceKm, elapsedTime, roundScore);	
		updateScorePanel(score);
	}
	
	private void showFlagPointInMap(Point point, int pictureId){
		Drawable drawable = resources.getDrawable(pictureId);
		PictureMarkerSymbol markerSymbol = new PictureMarkerSymbol(drawable);
		markerSymbol.setOffsetX(10);
		markerSymbol.setOffsetY(13);		
		Graphic graphic = new Graphic(point, markerSymbol);		
		locationsLayer.addGraphic(graphic);
	}

	private double getKmDistanceFromTarget(Point pointClicked){
		Point currentTarget = currentLocation.getMapPoint();
		double distance = GeometryEngine.distance(pointClicked, currentTarget, map.getSpatialReference());
		Unit mapUnit = map.getSpatialReference().getUnit();
		double distanceKm = Unit.convertUnits(distance,mapUnit, Unit.create(LinearUnit.Code.KILOMETER));		
		return distanceKm;
	}
	
	private void showRoundScore(double distanceKm, long elapsedTime, long roundScore){
		ScoreDialog dialog = new ScoreDialog(context, 
											 currentLocation.getName(), 
											 Math.round(elapsedTime/1000),
											 Math.round(distanceKm),
											 roundScore);
		
		dialog.addListener(new IScoreDialogListener() {			
			public void nextRound() {
				nextGameRound();
			}		
			public void stopGame() {
			
			}
		});		
		dialog.show();
	}
	
	private void updateScorePanel(long totalScore){
		panelManager.updatePanel(totalScore, 1, 1500);
	}	
}