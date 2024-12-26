function Step({ startStop, busLine, time, stops, endStop }) {
  return (
    <div className="step">
      <div className="sideBar">
        <img className="busicon" src={require("../bus image.png")} alt="bus" />
      </div>
      <div className="stepContent">
        <div className="start">{startStop}</div>
        <div className="lineNumber">{busLine}</div>
        <div className="time">{time} minutes</div>
        <div className="stops">
          Ride {stops.length + 1} stops:
          <ul className="stopsList">
            {stops.map((stop, index) => (
              <li key={index}>{stop}</li>
            ))}
          </ul>
        </div>
        <div className="end">{endStop}</div>
      </div>
    </div>
  );
}

export default Step;
