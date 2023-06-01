const apik = "NX6B7NP7UE4HBST9CRF23Q6Q7"

function retrieveElements() {
    const inputval = document.querySelector('#cityinput').textContent;
    const description = document.querySelector('#description');
    const temp = document.querySelector('#temp');
    const wind = document.querySelector('#wind');
    const snow = document.querySelector('#snow');
    const snowdepth = document.querySelector('#snowdepth');
    return {inputval, description, temp, wind, snow, snowdepth};
}

$(function () {
    const {
        inputval,
        description,
        temp,
        wind,
        snow,
        snowdepth
    } = retrieveElements();

    const URL = `https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/${inputval}?unitGroup=metric&key=${apik}&contentType=json`;
    console.log(URL);
    $.ajax({
        url: URL,
        type: 'GET',
        success: function (result) {
            const weatherData = Weather.fromJson(result);

            temp.innerHTML = `Weather: <span>${weatherData.temp} °C</span>`
            description.innerHTML = `Sky Conditions: <span>${weatherData.description}<span>`
            wind.innerHTML = `Wind Speed: <span>${weatherData.windspeed} km/h<span>`
            snow.innerHTML = `Snow: <span>${weatherData.snow} cm<span>`
            snowdepth.innerHTML = `Snow Depth: <span>${weatherData.snowdepth} cm<span>`

        }, error: function (error) {
            alert('Error ${error}')
        }
    });
});

class Weather {
    constructor(description, temp, windspeed, snow, snowdepth) {
        this.description = description;
        this.temp = temp;
        this.windspeed = windspeed;
        this.snow = snow;
        this.snowdepth = snowdepth;
    }

    static fromJson(json) {
        return new Weather(
            json.description,
            json['days']['0']['temp'],
            json['days']['0']['windspeed'],
            json['days']['0']['snow'],
            json['days']['0']['snowdepth']
        );
    }
}