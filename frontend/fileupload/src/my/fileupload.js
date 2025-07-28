import React,{useState} from "react";

function FileUpload() {
    const [file,setFile] = useState(null);
    const [status,setStatus] = useState("");

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    }

    const getCsrfToken = async () => {
        const res = await fetch("http://localhost:8081/api/csrf-token", { credentials: "include" }); // 쿠키 포함
        const data = await res.json();
        return data.token;
    };

    const handleFileUpload = async ()=> {

        const csrfToken = await getCsrfToken();

        if(!file) {
            alert("파일을 선택해 주세요!");
            return;
        }
        const formData = new FormData();
        formData.append("file",file);

        try {
            const res = await fetch("http://localhost:8081/api/fileUpload",{
                method:"POST",
                body:formData,
                headers: {
                    "X-CSRF-TOKEN": csrfToken, // 필수!
                },
                credentials:"include"
            });
            if (res.ok) {
                const text = await res.text();
                setStatus("업로드 성공: " + text);
            } else {
                setStatus("업로드 실패");
            }
        } catch (err) {
            setStatus("에러 발생: "+err.message);
        }
    };

    return (
        <div>
            <input type="file" onChange={handleFileChange} />
            <button onClick={handleFileUpload}>업로드</button>
            <p>{status}</p>
        </div>
    )
}
export default FileUpload;
