import { ArrowLeft } from "lucide-react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "@styles/pages-styles/SettingsPage.css";
import { useAuth } from "@/context/AuthContext";
import { deleteUserAccount } from "@/api/service/UserService";
import { toast } from "react-toastify";
import { deleteAllUsersPosts } from "@/api/service/PostingService";

export default function SettingsPage() {
    useEffect(() => { document.title = "Settings" }, []);
    const navigate = useNavigate();
    const { session, setSession } = useAuth();
    const authUser = session.user;

    const [passwordForDeleting, setPasswordForDeleting] = useState<string>("");

    async function deleteAccount() {
        try {
            if (authUser && passwordForDeleting) {
                const deletingPostsResult = await deleteAllUsersPosts(authUser.userId);
                console.log(deletingPostsResult);
                if (deletingPostsResult.data === "success") {
                    const deletingAccountResult = await deleteUserAccount(authUser.username, passwordForDeleting);
                    console.log("THIS ISTHE ACCOUNT RESULT - ", deletingAccountResult);
                    if (deletingAccountResult.data === "success") {
                        setSession({ user: null, isLoggedIn: false });
                        navigate("/auth");
                        toast("Your account has been deleted successfully.");
                    } else if (deletingAccountResult.data === "failure: incorrect password") {
                        toast("Incorrect password. Account not deleted.");
                    } else {
                        toast("Failed to delete your account.");
                    }
                } else {
                    toast("Failed to delete user’s posts, account not deleted.");
                }
            }
        } catch (error) {
            console.error(error);
            toast("An error occurred while deleting your account.");
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
                <span className="notificationHeaderHeadingSpan">Settings</span>
            </div>

            <div className="settingsFormContainer">
                <span className="settingsFormHeading" style={{ color: 'red' }}>Delete account</span>
                <span style={{ fontWeight: 'bold', fontStyle: 'italic', color: "grey" }}>Note: This account is irreversible.</span>
                <form className="formItself" onSubmit={(e) => {
                    e.preventDefault();
                    deleteAccount();
                }}>
                    <div className="formRow">
                        <input
                            type="password"
                            value={passwordForDeleting}
                            placeholder="Password"
                            onChange={(e) => setPasswordForDeleting(e.target.value)}
                            className="formInputField"
                        />
                        <button
                            className="formSubmitButton"
                            disabled={!passwordForDeleting ? true : false}
                            type="submit"
                        >Delete</button>
                    </div>
                </form>
            </div>



        </div>
    )
}