// ./gradlew bootRun
import "./App.css";
import { useState } from "react";
import Control from "./components/Control";
import EndPlace from "./components/EndPlace";
import StartPlace from "./components/StartPlace";
import TotalTime from "./components/TotalTime";
import Step from "./components/Step";

function App() {
  const [problem, setProblem] = useState();
  const [steps, setSteps] = useState();
  const [totalTime, setTotalTime] = useState();
  const [startStep, setStartStep] = useState();
  const [endStep, setEndStep] = useState();

  function onclick(startingPoint, destination) {
    console.log("Button Clicked!");
    console.log("Start Point:", startingPoint);
    console.log("Destination:", destination);

    setProblem(null);
    setSteps(null);
    setStartStep(null);
    setEndStep(null);
    setTotalTime(null);

    if (!startingPoint || !destination) {
      setProblem(
        <div className="Totaltime red">
          {startingPoint ? "You didn't enter a destination point" : "You didn't enter a starting point"}
        </div>
      );
      return;
    }

    if (startingPoint === destination) {
      setProblem(<div className="Totaltime red">Starting point and destination cannot be the same.</div>);
      return;
    }

    const message = { start: startingPoint, end: destination };
    fetch("http://localhost:8080/api", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(message),
    })
      .then((response) => {
        if (!response.ok) throw new Error("Network response was not OK");
        return response.json();
      })
      .then((allData) => {
        console.log("API Response:", allData);

        if (allData.status === "noEnd") {
          setProblem(<div className="Totaltime red">There is no such a destination in the map.</div>);
        } else if (allData.status === "noStart") {
          setProblem(<div className="Totaltime red">There is no such a starting point in the map.</div>);
        } else {
          setTotalTime(allData.totalTime);
          setStartStep(
            <StartPlace
              place={allData.startPlace}
              vertex={allData.startPlaceVertex}
              walkTime={allData.startWalkTime}
            />
          );
          setEndStep(
            <EndPlace
              place={allData.endPlace}
              vertex={allData.endPlaceVertex}
              walkTime={allData.endWalkTime}
            />
          );

          setSteps(
            allData.steps.map((step, index) => (
              <Step
                key={index}
                startStop={step.startVertex}
                busLine={step.line}
                time={step.time}
                stops={step.stops}
                endStop={step.endVertex}
              />
            ))
          );
        }
      })
      .catch((error) => {
        setProblem(<div className="Totaltime red">Couldn't connect to the Server</div>);
        console.error("Error:", error.message);
      });
  }

  return (
    <div className="App">
      <div className="mapDiv">
        <img className="mapImage" src={require("./londonMap updated.png")} alt="londonMap" />
      </div>
      <div className="contentDiv">
        <Control onclick={onclick} />
        <div className="steps">
          {problem}
          {totalTime && <TotalTime time={totalTime} />}
          {startStep}
          {steps}
          {endStep}
        </div>
      </div>
    </div>
  );
}

export default App;
