import { useState } from "react";
export default function Control({ onclick }) {
    const [startingPoint, setStartingPoint] = useState(null);
    const [destination, setDestination] = useState(null);
  
    const handleButtonClick = () => {
      onclick(startingPoint, destination);
  
    };
  
    return (
      <div className="controlDiv">
        <label className="item label">Starting Point: </label>
        <input
          className="startingbox item textbox"
          type="text"
          value={startingPoint}
          onChange={(e) => setStartingPoint(e.target.value)}
        />
  
        <label className="item label">Destination: </label>
        <input
          className="destbox item textbox"
          type="text"
          value={destination}
          onChange={(e) => setDestination(e.target.value)}
        />
  
        <button className="submitbutton item button" onClick={handleButtonClick}>
          Directions
        </button>
      </div>
    );
  }