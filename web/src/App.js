import logo from './logo.svg';
import './App.css';
import { useState } from 'react';

function App() {

  const [problem, setProblem] = useState();
  const [steps, setsteps] = useState();
  const [totalTime, setTotalTime] = useState();
  const [startStep, setStartStep] = useState();
  const [endStep, setEndStep] = useState();
  

  function onclick(startingPoint, destination) {

    console.log("button Clicked!!")
    console.log("start point: " + startingPoint)
    console.log("destination: " + destination)

    setProblem(<></>);
    setEndStep(<></>);
    setStartStep(<></>);
    setTotalTime(<></>);
    setsteps(<></>);

    if(startingPoint == destination) {
      
    }

    const message = {
      startingPoint: startingPoint,
      destination: destination
    };
  
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : '*'},
      body: JSON.stringify(message)
    };

    fetch('http://localhost:8080/api', requestOptions)
    .then(function(response) {
      if (response.ok) {
        return response.json();
      }
      setProblem(
        <div className='Totaltime red'>
            Network response was not OK
          </div>
      );
      throw new Error('Network response was not OK');
    }).then(function(allData) {

      console.log(allData);
      
      if(allData.status == "noEnd") {
        setProblem(
          <div className='Totaltime red'>
            there is no such a destination in the map.
          </div>
        )
        
      }
      else if(allData.status == "noStart") {
        setProblem(
          <div className='Totaltime red'>
            there is no such a starting point in the map.
          </div>
        )
        
      }
      else {
        
        setTotalTime(
          <TotalTime time={allData.totalTime} />
        );
        setStartStep(
          <StartPlace place={allData.startPlace} vertex={allData.startPlaceVertex} walkTime={allData.startWalkTime} />
        );
        setEndStep(
          <EndPlace place={allData.endPlace} vertex={allData.endPlaceVertex} walkTime={allData.endWalkTime} />
        );
  
        let counter = 0;
        setsteps(
          allData.steps.map(function(step) {
            counter++;
            return (<Step 
             key={counter}
              startStop={step.startVertex} 
              busLine={step.line} 
              time={step.time} 
              stops={step.stops}
              endStop={step.endVertex}
            />)
          }
        ));

      }
      
    }).catch(function(error) {

      setProblem(
        <div className='Totaltime red'>
          Couldn't connect to the Server
        </div>
      )

      console.log('Error:', error.message);
    });
  }


  return (
    <div className="App">
      <div className='mapDiv'>
        <img className="mapImage" src={require('./londonMap updated.png')} alt="londonMap"/>
      </div>
      <div className="contentDiv">

        <Control onclick={onclick}/>

        <div className="steps">

          {problem}
          {totalTime}
          {startStep}
          {steps}
          {endStep}

        </div>

      </div> 
    </div>
  );
}

function Control({onclick}) {
  
  let startingPoint = "";
  let destination = "";

  return(
    <div className="controlDiv">
      
        <label className="item label">Starting Point: </label>
        <input className="item textbox" type="text" onChange={(e) => {startingPoint = e.target.value}}></input>
      
        <label className="item label">Destination: </label>
        <input className="item textbox" type="text" onChange={(e) => {destination = e.target.value}}></input>
  
        <button className="item button" onClick={() => {onclick(startingPoint, destination)}}>Directions</button>
      
    </div>
  );
}
function TotalTime({time}) {

  return(
    <div className="step">
      <div className='Totaltime'>Journey should take {time} minutes</div>
    </div>
  );
}
function StartPlace({place, vertex, walkTime}) {

  if(place === "none")
    return <></>

  return(
    <div className="step">
      <div className="sideBar">
        <img className="walkicon" src={require('./walkImage.png')} alt="londonMap"/>
      </div>
      <div className="stepContent">
        <div className='start'>{place}</div>
        <div className='lineNumber'>Walk</div>
        <div className='time'>{walkTime} minutes</div>
        <div className='end'>{vertex} Station</div>
      </div>
    </div>
  );
}
function EndPlace({place, vertex, walkTime}) {

  if(place === "none")
  return <></>

  return(
    <div className="step">
      <div className="sideBar">
      <img className="walkicon" src={require('./walkImage.png')} alt="londonMap"/>
      </div>
      <div className="stepContent">
        <div className='start'>{vertex} Station</div>
        <div className='lineNumber'>Walk</div>
        <div className='time'>{walkTime} minutes</div>
        <div className='end'>{place}</div>
      </div>
    </div>
  );
}
function Step({startStop, busLine, time, stops, endStop}) {

  let counter = 0;
  const listItems = stops.map(function(stop) {
    counter++
    return <li key={counter}>{stop.getStop}</li>;
  });

  let numberStops = "";

  if(stops.length === 0) {
    numberStops = "ride " + 1 + " stop";
  }
  else {
    numberStops = "ride " + (stops.length + 1) + " stops:";
  }

  return(
    <div className="step">
      <div className="sideBar">
      <img className="busicon" src={require('./bus image.png')} alt="londonMap"/>
      </div>
      <div className="stepContent">
        <div className='start'>{startStop}</div>
        <div className='lineNumber'>{busLine}</div>
        <div className='time'>{time} minutes</div>
        <div className='stops'>
          {numberStops}
          <ul className='stopsList'>{listItems}</ul>
        </div>
        <div className='end'>{endStop}</div>
      </div>
    </div>
  );
}

export default App;
