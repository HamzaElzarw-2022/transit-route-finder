function StartPlace({ place, vertex, walkTime }) {
  return (
    <div className="step">
      <div className="sideBar">
        <img className="walkicon" src={require("../walkImage.png")} alt="walk" />
      </div>
      <div className="stepContent">
        <div className="startpoint">{place}</div>
        <div className="lineNumber">Walk</div>
        <div className="time">{walkTime} minutes</div>
        <div className="end">{vertex} Station</div>
      </div>
    </div>
  );
}

export default StartPlace;
