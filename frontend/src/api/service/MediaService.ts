import axios from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';

//UPLOADING MEDIA METHODS
export async function uploadMedia(file: File | null){
    if (file) {
        const formData = new FormData();
        formData.append("media", file);

        return await axios.post(`${SPRING_BOOT_LOCALHOST}/media/uploadMedia`, formData, {
            withCredentials: true,
            headers: {
                "Content-Type": "multipart/form-data"
            },
        });
    }
    return null;
}


// DELETE MEDIA FROM CLOUDINARY
export async function deleteMedia(imageUrl:string){
    return await axios.post(`${SPRING_BOOT_LOCALHOST}/media/deleteMedia`, imageUrl, {
        withCredentials: true
    });
}