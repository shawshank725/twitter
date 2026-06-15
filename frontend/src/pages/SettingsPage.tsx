import { ArrowLeft, ChevronDown, ChevronRight } from "lucide-react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "@styles/pages-styles/SettingsPage.css";

export default function SettingsPage() {
    useEffect(() => { document.title = "Settings" }, []);
    const navigate = useNavigate();
    const [openSection, setOpenSection] = useState<string | null>(null);

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

            <div
                className="accordion"
                onClick={() =>
                    setOpenSection(openSection === "account" ? null : "account")
                }
            >
                <p className="accordionText">Account</p>
                {openSection === "account"
                    ? <ChevronDown size={20} />
                    : <ChevronRight size={20} />}
            </div>

            {openSection === "account" && (
                <div className="panel">
                    <span onClick={() => { navigate('/settings/changeUsername')}}>Change username</span>
                    <span>Change email</span>
                    <span onClick={() => { navigate('/settings/changePassword')}}>Change password</span>
                    <span style={{ color: "red" }}>Deactivate account</span>
                    <span style={{ color: "red" }} onClick={() => { navigate('/settings/deleteAccount')}}>Delete account</span>
                </div>
            )}

            <div
                className="accordion"
                onClick={() =>
                    setOpenSection(openSection === "privacy" ? null : "privacy")
                }
            >
                <p className="accordionText">Privacy</p>
                {openSection === "privacy"
                    ? <ChevronDown size={20} />
                    : <ChevronRight size={20} />}
            </div>

            {openSection === "privacy" && (
                <div className="panel">
                    <span>Private account</span>
                    <span>Who can mention me</span>
                    <span>Who can message me</span>
                    <span>Hide likes</span>
                    <span>Hide bookmarks</span>
                </div>
            )}

            <div
                className="accordion"
                onClick={() =>
                    setOpenSection(openSection === "notifications" ? null : "notifications")
                }
            >
                <p className="accordionText">Notifications</p>
                {openSection === "notifications"
                    ? <ChevronDown size={20} />
                    : <ChevronRight size={20} />}
            </div>

            {openSection === "notifications" && (
                <div className="panel">
                    <span>Like notifications</span>
                    <span>Reply notifications</span>
                    <span>Mention notifications</span>
                    <span>Follow notifications</span>
                </div>
            )}

            <div
                className="accordion"
                onClick={() =>
                    setOpenSection(openSection === "data" ? null : "data")
                }
            >
                <p className="accordionText">Data & Security</p>
                {openSection === "data"
                    ? <ChevronDown size={20} />
                    : <ChevronRight size={20} />}
            </div>

            {openSection === "data" && (
                <div className="panel">
                    <span>Download archive</span>
                    <span>Active sessions</span>
                    <span>Logout from all devices</span>
                </div>
            )}


        </div>
    )
}