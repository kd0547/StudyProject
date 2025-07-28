import React, {useEffect, useState} from "react";
import FileUpload from "./fileupload";
import "../css/ModernButton.css";
function MyWindows() {
    const [isOpen,setIsOpen] = useState(false);
    const [message, setMessage] = useState("");

    useEffect(() => {
        if(isOpen) {
            fetch("/api/message")
                .then((res)=> res.text())
                .then((data)=> {
                    setMessage(data);
                })
                .catch((err) => {
                    setMessage("서버 요청 실패!");
                })
        }
    }, [isOpen]);



    return (
        <div style = {{padding:'20px'}}>
            <button  className="modern-btn"  onClick={()=> setIsOpen(true)}>창 열기</button>
            {isOpen && (
                <div style={{
                    position: 'fixed',
                    width: '300px',
                    top: '50%',
                    left: '50%',
                    transform:'translate(-50%, -50%)',
                    padding: '20px',
                    backgroundColor: 'white',
                    border: '1px solid #ccc',
                    boxShadow: '0 0 10px rgba(0,0,0,0.3)',
                    zIndex: 1000
                }}>
                    <FileUpload/>
                    <button onClick={() => setIsOpen(false)}>닫기</button>
                </div>
            )}

        </div>

    );
}

export default MyWindows;