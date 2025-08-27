import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import java.util.Queue;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import weather.Period;
import weather.WeatherAPI;

import javafx.scene.image.Image;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;


public class JavaFX extends Application {
	Label detailedForecast,temperature,weather,wind,windDirection,nightTemp, dayTemp,day,precipitationText;
	Button toMainScreenButton, toSecondScreenButton,changeTempToCel, changeTempToFahrenheit,goBackToSecond,dayButton;
	Scene mainScene,introScene, secondScene,dayDetailScene;
	Stage primaryStage;
	Image image;
	Image moonImage = new Image(("moon.png"));
	Image sunImage = new Image(("sun.png"));
	Image dropletImage = new Image(("drop.png"));
	Image windImage = new Image(("wind.png"));
	Image infoImage = new Image(("info2.png"));
	ImageView drop = new ImageView(dropletImage);
	ArrayList<Period> forecast;
	HBox hBox,hboxRow1,hboxRow2,hBoxRow3,hBoxRow4,hBoxRow5;
	VBox mainLayout,buttonLayout;
	ImageView imageView = new ImageView(image);
	ImageView imageViewSun = new ImageView(sunImage);
	ImageView imageViewDroplet = new ImageView(dropletImage);
	ImageView imageViewMoon = new ImageView(moonImage);
	ImageView imageViewWind = new ImageView(windImage);
	ImageView imageViewInfo = new ImageView(infoImage);
	ScrollPane scrollPane;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		primaryStage = stage;
		primaryStage.setTitle("Weather App");

		setupIntroScene();

        primaryStage.setScene(introScene);
		primaryStage.show();
	}

	private void setupIntroScene()
	{
		Image mainLogo = new Image(("logo.png"));
		ImageView mainLogoView = new ImageView(mainLogo);
		mainLogoView.setFitHeight(180);
		mainLogoView.setFitWidth(180);

		Button todaysButton = new Button("Today's Forecast");
		todaysButton.setFont(new Font("Arial", 35));
		todaysButton.setStyle("-fx-background-radius:10; -fx-background-color:linear-gradient(to top right, #4facfe, #00f2fe);-fx-text-fill:#00000f; -fx-padding: 8 15;");
		todaysButton.setOnAction(e -> {setupMainScene();primaryStage.setScene(mainScene);});
		VBox vBoxMain = new VBox(mainLogoView, todaysButton);
		vBoxMain.setStyle("-fx-background-color:LIGHTBLUE;");
		vBoxMain.setSpacing(100);
		vBoxMain.setAlignment(Pos.CENTER);

		introScene = new Scene(vBoxMain,700,700);

	}

	private void setupMainScene() {
		forecast = WeatherAPI.getForecast("LOT", 77, 70);
		if (forecast == null) {
			throw new RuntimeException("Forecast did not load");
		}
		getTheWeatherBackground(forecast.get(0).shortForecast);

		Label cityLabel = new Label("Chicago, IL");
		cityLabel.setFont(Font.font("Arial", FontWeight.BOLD,30));
		cityLabel.setAlignment(Pos.CENTER);

		day = new Label("Today");
		day.setMaxWidth(Double.MAX_VALUE);
		day.setAlignment(Pos.CENTER);
		day.setFont(Font.font("Arial", FontWeight.BOLD,40));
		day.setStyle("-fx-text-fill: #00000f;");

		//get temp using the template this could help
		//changing the background and color based on
		//temp which all will go in same template

		Period today = forecast.get(0);
		weatherTemplate dayW = new dayWeather(today);
		int temp = dayW.showTemp();

		temperature = new Label(temp + "°F");
		temperature.setMaxWidth(Double.MAX_VALUE);
		temperature.setAlignment(Pos.CENTER);
		temperature.setFont(Font.font("Arial", 60));
		temperature.setStyle("-fx-text-fill: #00000f;");

		wind = new Label("Wind: " + forecast.get(0).windSpeed );
		wind.setMaxWidth(Double.MAX_VALUE);
		wind.setAlignment(Pos.CENTER);
		wind.setFont(Font.font("Arial",40));
		wind.setStyle("-fx-text-fill: #00000f;");

		windDirection = new Label(" - " + forecast.get(0).windDirection);
		windDirection.setMaxWidth(Double.MAX_VALUE);
		windDirection.setAlignment(Pos.CENTER);
		windDirection.setFont(Font.font("Arial",40));
		windDirection.setStyle("-fx-text-fill: #00000f;");

		weather = new Label("Condition: " + forecast.get(0).shortForecast);
		weather.setWrapText(true);
		weather.setMaxWidth(Double.MAX_VALUE);
		weather.setAlignment(Pos.CENTER);
		weather.setFont(Font.font("Arial",35));
		weather.setStyle("-fx-text-fill: #00000f;");


		toSecondScreenButton = new Button("Next 5-days Forecast");
		toSecondScreenButton.setFont(new Font("Arial", 35));
		toSecondScreenButton.setStyle("-fx-background-radius:10; -fx-background-color:linear-gradient(to top right, #4facfe, #00f2fe);-fx-text-fill:#00000f; -fx-padding: 8 15;");


		String precipitation = forecast.get(0).probabilityOfPrecipitation.value +"%";
		precipitationText = new Label("   Precipitation: " + precipitation);
		precipitationText.setMaxWidth(Double.MAX_VALUE);
		precipitationText.setFont(Font.font("Arial", 30));
		precipitationText.setStyle("-fx-text-fill: #00000f;");
		precipitationText.setAlignment(Pos.CENTER);
		hboxRow2 = new HBox(imageViewDroplet,precipitationText);
		hboxRow2.setAlignment(Pos.CENTER);

		changeTempToCel = new Button("Convert to °C");
		changeTempToFahrenheit = new Button("Convert to °F");
		changeTempToCel.setStyle("-fx-background-radius: 25; -fx-background-color: linear-gradient(to right, #a44ffe, #00f2fe); -fx-text-fill: #00000f; -fx-padding: 8 15;");
		changeTempToFahrenheit.setStyle("-fx-background-radius: 25; -fx-background-color: linear-gradient(to right, #a44ffe, #00f2fe); -fx-text-fill: #00000f; -fx-padding: 8 15;");
		changeTempToFahrenheit.setDisable(true);

		hBox = new HBox(changeTempToCel, changeTempToFahrenheit);
		hBox.setAlignment(Pos.CENTER);

		imageViewWind.setFitWidth(34);
		imageViewWind.setFitHeight(34);
		hboxRow1 = new HBox(imageViewWind,wind,windDirection);
		hboxRow1.setAlignment(Pos.CENTER);

		hboxRow2 = new HBox();
		drop.setFitWidth(34);
		drop.setFitHeight(34);
		hboxRow2.getChildren().add(drop);
		hboxRow2.setAlignment(Pos.CENTER);
		hboxRow2.getChildren().add(precipitationText);

		Button back = new Button("Main Screen");
		back.setFont(new Font("Arial", 35));
		back.setStyle("-fx-background-radius:10; -fx-background-color:linear-gradient(to top right, #4facfe, #00f2fe);-fx-text-fill:#00000f; -fx-padding: 8 15;");
		back.setOnAction(e -> {setupMainScene();primaryStage.setScene(introScene);});

		mainLayout = new VBox(imageView,cityLabel,day,temperature,hBox,hboxRow2, weather,hboxRow1);
		mainLayout.setAlignment(Pos.CENTER);
		hBoxRow3 = new HBox(back,toSecondScreenButton);
		hBoxRow3.setSpacing(15);
		buttonLayout = new VBox(mainLayout,hBoxRow3);
		buttonLayout.setAlignment(Pos.CENTER);
		mainLayout.setSpacing(30);
		buttonLayout.setSpacing(30);

		buttonLayout.setStyle("-fx-padding: 25;" + "-fx-background-color: #fbf9f9;");

		scrollPane = new ScrollPane(buttonLayout);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		mainScene = new Scene(scrollPane,700,700);

		//showing the example of adapter of how it can be used
		//passing the period so that can have accurate temp
		weatherAdapt usingAdapt = new weatherAdapt(forecast.get(0));

		changeTempToCel.setOnAction(e -> {
			int tempC = usingAdapt.getTemp();
			temperature.setText(tempC + "°C");
			changeTempToCel.setDisable(true);
			changeTempToFahrenheit.setDisable(false);
		});

		changeTempToFahrenheit.setOnAction(e -> {
			temperature.setText(forecast.get(0).temperature + "°F");
			changeTempToCel.setDisable(false);
			changeTempToFahrenheit.setDisable(true);
		});

		toSecondScreenButton.setOnAction(e -> {setupSecondScene();primaryStage.setScene(secondScene);});
	}

	private void setupSecondScene() {
		forecast = WeatherAPI.getForecast("LOT", 77, 70);

		hboxRow1 = new HBox(20);
		hboxRow1.setAlignment(Pos.CENTER);
		hboxRow2 = new HBox(20);
		hboxRow2.setAlignment(Pos.CENTER);

		for (int i = 1; i <= 5; i++) {
			String weatherCondition = forecast.get(i * 2 - 1).shortForecast;
			String gradientColor = getWeatherColorGradient(weatherCondition);

			String nextDay = LocalDate.now().plusDays(i).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

			dayButton = new Button(nextDay + "\n" + forecast.get(i).temperature);
			dayButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			dayButton.setPrefWidth(150);
			dayButton.setPrefHeight(100);
			dayButton.setStyle("-fx-background-radius: 20; -fx-background-color: " + gradientColor + "; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 6, 0, 0, 4);");

			int finalI = i;
			dayButton.setOnAction(e -> {
					setupDayDetailScene(finalI);primaryStage.setScene(dayDetailScene);});

			if (i <= 2) {
				hboxRow1.getChildren().add(dayButton);
			} else {
				hboxRow2.getChildren().add(dayButton);
			}

		}
		toMainScreenButton = new Button("Go Back");
		toMainScreenButton.setFont(Font.font("Arial", FontWeight.BOLD, 26));
		toMainScreenButton.setStyle("-fx-background-radius: 25; -fx-background-color: linear-gradient(to right, rgba(79,94,254,0.99), #00f2fe); -fx-text-fill: white; -fx-padding: 10 20;");
		toMainScreenButton.setOnAction(e -> primaryStage.setScene(mainScene));

		imageViewInfo.setFitHeight(40);
		imageViewInfo.setFitWidth(40);

		StackPane.setAlignment(imageViewInfo,Pos.TOP_RIGHT);
		Label popupText = new Label("Click on any of the days to see detailed weather information.");
		popupText.setVisible(false);
		popupText.setStyle("-fx-font-size: 16px; -fx-text-fill: black; -fx-background-color: white;");


		PauseTransition pauseTransition = new PauseTransition(Duration.seconds(10));
		pauseTransition.setOnFinished(e -> {popupText.setVisible(false);});
		pauseTransition.play();

		mainLayout = new VBox(40,hboxRow1, hboxRow2, toMainScreenButton);
		hBoxRow5 = new HBox(popupText);
		hBoxRow5.setAlignment(Pos.TOP_CENTER);
		VBox spacing = new VBox(imageViewInfo,hBoxRow5,mainLayout);

		mainLayout.setAlignment(Pos.CENTER);
		spacing.setSpacing(80);
		spacing.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(700, 700, true, true, true, true))));

		imageViewInfo.setOnMouseClicked(event -> {
			popupText.setVisible(true);});


		secondScene = new Scene(spacing, 700, 700);

	}

	private void setupDayDetailScene(int dayIndex) {
		getTheWeatherBackground(forecast.get(dayIndex*2 -1).shortForecast);

		imageView = new ImageView(image);
		Period periodDay = forecast.get(dayIndex * 2 - 1);
		Period periodNight = forecast.get(dayIndex * 2);

		String dayName = LocalDate.now().plusDays(dayIndex).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

		day = new Label(dayName);
		day.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 36));
		day.setTextFill(Color.BLACK);

		temperature = new Label(forecast.get(dayIndex * 2 -1).temperature + "°F");
		temperature.setMaxWidth(Double.MAX_VALUE);
		temperature.setAlignment(Pos.CENTER);
		temperature.setFont(Font.font("Arial", 60));
		temperature.setStyle("-fx-text-fill: #00000f;");

		imageViewSun.setFitHeight(30);
		imageViewSun.setFitWidth(30);

		dayTemp = new Label("   Day: " + periodDay.temperature + "°F");
		dayTemp.setFont(Font.font("Arial",  28));
		dayTemp.setTextFill(Color.BLACK);

		imageViewMoon.setFitHeight(30);
		imageViewMoon.setFitWidth(30);

		nightTemp = new Label("   Night: " + periodNight.temperature + "°F");
		nightTemp.setFont(Font.font("Arial", 28));
		nightTemp.setTextFill(Color.BLACK);

		detailedForecast = new Label("Summary:\n" + periodDay.detailedForecast);
		detailedForecast.setAlignment(Pos.CENTER);
		detailedForecast.setWrapText(true);
		detailedForecast.setMaxWidth(Double.MAX_VALUE);
		detailedForecast.setFont(Font.font("Arial" ,20));
		detailedForecast.setTextFill(Color.BLACK);

		imageViewDroplet.setFitHeight(30);
		imageViewDroplet.setFitWidth(30);

		precipitationText = new Label("   Precipitation: " + periodDay.probabilityOfPrecipitation.value + "%");
		precipitationText.setFont(Font.font("Arial", 24));
		precipitationText.setTextFill(Color.BLACK);

		changeTempToCel = new Button("Convert to °C");
		changeTempToCel.setStyle("-fx-background-radius: 25; -fx-background-color: linear-gradient(to right, #4facfe, #00f2fe); -fx-text-fill: #00000f; -fx-padding: 8 15;");
		changeTempToFahrenheit = new Button("Convert to °F");
		changeTempToFahrenheit.setStyle("-fx-background-radius: 25; -fx-background-color: linear-gradient(to right, #4facfe, #00f2fe); -fx-text-fill: #00000f; -fx-padding: 8 15;");
		changeTempToFahrenheit.setDisable(true);

		changeTempToCel.setOnAction(e -> {
			int dayTempC = (periodDay.temperature - 32) * 5 / 9;
			int nightTempC = (periodNight.temperature - 32) * 5 / 9;
			int tempC = (forecast.get(dayIndex * 2 -1).temperature -32) * 5 / 9;
			dayTemp.setText("   Day: " + dayTempC + "°C");
			nightTemp.setText("   Night: " + nightTempC + "°C");
			temperature.setText(tempC + "°C");
			changeTempToCel.setDisable(true);
			changeTempToFahrenheit.setDisable(false);
		});

		changeTempToFahrenheit.setOnAction(e -> {
			dayTemp.setText("   Day: " + periodDay.temperature + "°F");
			nightTemp.setText("   Night: " + periodNight.temperature + "°F");
			temperature.setText(forecast.get(dayIndex * 2 -1).temperature + "°F");
			changeTempToCel.setDisable(false);
			changeTempToFahrenheit.setDisable(true);
		});


		wind = new Label("Wind: " + periodDay.windSpeed);
		wind.setFont(Font.font("Arial", 24));
		wind.setTextFill(Color.BLACK);

		windDirection = new Label(" - " + periodDay.windDirection);
		windDirection.setFont(Font.font("Arial", 24));
		windDirection.setTextFill(Color.BLACK);

		hBox = new HBox( changeTempToCel, changeTempToFahrenheit);
		hBox.setAlignment(Pos.CENTER);

		imageViewWind.setFitWidth(34);
		imageViewWind.setFitHeight(34);
		hboxRow1 = new HBox(imageViewWind,wind, windDirection);
		hboxRow1.setAlignment(Pos.CENTER);

		hboxRow2 = new HBox(imageViewSun,dayTemp);
		hboxRow2.setAlignment(Pos.CENTER);

		hBoxRow3 = new HBox(imageViewMoon,nightTemp);
		hBoxRow3.setAlignment(Pos.CENTER);

		hBoxRow4 = new HBox(imageViewDroplet,precipitationText);
		hBoxRow4.setAlignment(Pos.CENTER);

		goBackToSecond = new Button("Back to Forecast");
		goBackToSecond.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		goBackToSecond.setStyle("-fx-background-radius: 25; -fx-background-color: linear-gradient(to right, #4176a5, #00f2fe); -fx-text-fill: #00000f; -fx-padding: 10 20;");
		goBackToSecond.setOnAction(e -> {primaryStage.setScene(secondScene);setupMainScene();});

		mainLayout = new VBox(imageView,day, temperature, hBox, hboxRow2, hBoxRow3, hBoxRow4,hboxRow1, detailedForecast,goBackToSecond);
		mainLayout.setStyle("-fx-padding: 25;" + "-fx-background-color: #ffffff;");
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.setSpacing(30);

		scrollPane = new ScrollPane(mainLayout);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		dayDetailScene = new Scene(scrollPane, 700, 700);
	}

	private String getWeatherColorGradient(String forecast) {
		forecast = forecast.toLowerCase();
		if (forecast.contains("rain") || forecast.contains("showers")) {
			return "linear-gradient(to right, #0f2027, #203a43, #2c5364)";
		} else if (forecast.contains("snow")) {
			return "linear-gradient(to right, #eef2f3, #8e9eab)";
		} else if (forecast.contains("sunny") || forecast.contains("clear")) {
			return "linear-gradient(to right, #f7971e, #ffd200)";
		} else if (forecast.contains("cloud")) {
			return "linear-gradient(to right, #bdc3c7, #2c3e50)";
		} else if (forecast.contains("thunder")) {
			return "linear-gradient(to right, #654ea3, #eaafc8)";
		} else {
			return "linear-gradient(to right, #4facfe, #00f2fe)";
		}
	}

	private void getTheWeatherBackground(String forecast) {
		forecast = forecast.toLowerCase();
		if (forecast.contains("rain") || forecast.contains("showers")) {
			image = new Image(("rainy.gif"));
		} else if (forecast.contains("snow")) {
			image = new Image(("snowy2.gif"));
		} else if (forecast.contains("sunny") || forecast.contains("clear")) {
			image = new Image(("sunny3.jpeg"));
		} else if (forecast.contains("cloudy")) {
			image = new Image(("cloudyBetter.gif"));
		} else if (forecast.contains("thunder")) {
			image = new Image(("Pink Love GIF.gif"));
		}
		imageView.setImage(image);
		imageView.setFitHeight(200);
		imageView.setFitWidth(230);
		imageView.setPreserveRatio(false);
	}

}
