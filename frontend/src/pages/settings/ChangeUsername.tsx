import { ArrowLeft, CheckCircleIcon, XCircleIcon } from "lucide-react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "@styles/pages-styles/SettingsPage.css";
import { useAuth } from "@/context/AuthContext";
import { handleLogout, updateUsername, usernameAvailable } from "@/api/service/UserService";
import { toast } from "react-toastify";


export default function ChangeUsername() {
    useEffect(() => { document.title = "Change username" }, []);
    const navigate = useNavigate();
    const { session } = useAuth();
    const authUser = session.user;
    const usernameOld = authUser?.username ?? "";
    const [username, setUsername] = useState<string>(usernameOld);
    const [password, setPassword] = useState<string>("");

    const [usernameFound, setUsernameFound] = useState<boolean>(false);

    useEffect(() => {
        const checkUsername = async (username: string) => {
            try {
                const result = await usernameAvailable(username);
                if (result === true) { setUsernameFound(false); }
                else { setUsernameFound(true); }
            }
            catch (error) {
                console.error(error);
            }
        }
        checkUsername(username);
    }, [username]);

    function validateUsername(username: string, usernameOld: string, usernameFound: boolean): boolean {
        if (!username || username.trim() === "") return false;
        if (username.length > 50) return false;
        if (username === usernameOld) return false;
        if (usernameFound) return false;
        return true;
    }

    async function handleUsernameChange() {
        try {
            if (password) {
                const result = await updateUsername(usernameOld, username, password);
                if (result.data === "success") {
                    await handleLogout();
                    toast("Your username has been changed successfully! Login again.");
                    setTimeout(() => {
                        window.location.href = "/auth";
                    }, 1500);
                }
                else if (result.data === "failure: passwords don't match") {
                    alert("Entered password is wrong.");
                }
                else {
                    toast("Failed to change the username. Please try again later.");
                }
            }
        }
        catch (error) {
            console.log(error);
        }
    }

    return (
        <div className="settingsPageContainer">
            <div className="notificationHeadingContainer">
                <div className="arrowLeftNotificationViewerHeaderContainer">
                    <ArrowLeft
                        className="arrowLeftNotificationViewerHeader"
                        size={20}
                        onClick={() => {
                            if (window.history.length > 1) {
                                navigate(-1);
                            } else {
                                navigate('/settings');
                            }
                        }}
                    />
                </div>
            </div>

            <div className="settingsFormContainer">
                <span className="settingsFormHeading">Change username</span>
                <form className="formItself" onSubmit={(e) => {
                    e.preventDefault();
                    handleUsernameChange();
                }}>
                    <div className="formRow">
                        <input
                            type="text"
                            value={username}
                            placeholder="Username"
                            onChange={(e) => setUsername(e.target.value)}
                            className="formInputField" />
                        <button
                            className="formSubmitButton"
                            disabled={!(validateUsername(username, usernameOld, usernameFound) && password && password.trim() !== "")}
                            type="submit"
                        >Change username</button>
                        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                            {
                                username === "" ? (
                                    <span style={{ color: "red" }}>Username can't be empty</span>
                                ) : username.length > 50 ? (
                                    <span style={{ color: "red" }}>Username must be less than 50 characters</span>
                                ) : username === usernameOld ? (
                                    null
                                ) : usernameFound ? (
                                    <XCircleIcon width={30} color="red" />
                                ) : (
                                    <CheckCircleIcon width={30} color="green" />
                                )
                            }
                        </div>
                    </div>
                    <div className="formRow">
                        <input
                            type="password"
                            value={password}
                            placeholder="Password"
                            onChange={(e) => setPassword(e.target.value)}
                            className="formInputField" />
                    </div>
                </form>
            </div>
        </div>
    )
}