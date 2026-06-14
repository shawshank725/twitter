import { ArrowLeft } from "lucide-react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "@styles/pages-styles/SettingsPage.css";
import { useAuth } from "@/context/AuthContext";
import { changePassword } from "@/api/service/UserService";
import { toast } from "react-toastify";


export default function ChangePassword() {
    useEffect(() => { document.title = "Settings" }, []);
    const navigate = useNavigate();

    const { session } = useAuth();
    const authUser = session.user;

    const [confirmPassword, setConfirmPassword] = useState<string>("");
    const [newPassword, setNewPassword] = useState<string>("");



    async function handlePasswordChange() {
        try {
            if (confirmPassword && newPassword && authUser) {
                const result = await changePassword(authUser?.username, confirmPassword, newPassword);
                console.log("THIS IS THE RESULT ", result);
                if (result.data === "success") {
                    toast("Your password has been changed successfully.");
                    setConfirmPassword("");
                    setNewPassword("");
                }
                else {
                    toast("Failed to change the password. Please try again later.");
                }
            }
        }
        catch (error) {

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
                                navigate('/home');
                            }
                        }}
                    />
                </div>
                <span className="notificationHeaderHeadingSpan">Change password</span>
            </div>

            <div className="settingsFormContainer">
                <span className="settingsFormHeading">Change Password</span>
                <form className="formItself" onSubmit={(e) => {
                    e.preventDefault();
                    handlePasswordChange();
                }}>
                    <div className="formRow">
                        <input
                            type="password"
                            value={confirmPassword}
                            placeholder="Confirm password"
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            className="formInputField"
                        />
                        <button
                            className="formSubmitButton"
                            disabled={confirmPassword === "" || newPassword === ""}
                        >Change password</button>
                    </div>
                    <div className="formRow">
                        <input
                            type="password"
                            value={newPassword}
                            placeholder="New password"
                            onChange={(e) => setNewPassword(e.target.value)}
                            className="formInputField"
                        />
                        <button
                            className="formSubmitButton"
                            onClick={() => { setConfirmPassword(""); setNewPassword(""); }}
                        >Clear all</button>
                    </div>
                </form>
            </div>
        </div>
    )
}