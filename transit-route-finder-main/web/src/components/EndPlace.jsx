  export default  function EndPlace({ place, vertex, walkTime }) {
    if (place === "none") return <></>;
  
    return (
      <div className="step">
        <div className="sideBar">
          <img
            className="walkicon"
            src={require("../walkImage.png")}
            alt="londonMap"
          />
        </div>
        <div className="stepContent">
          <div className="start">{vertex} Station</div>
          <div className="lineNumber">Walk</div>
          <div className="time">{walkTime} minutes</div>
          <div className="end">{place}</div>
        </div>
      </div>
    );
  }