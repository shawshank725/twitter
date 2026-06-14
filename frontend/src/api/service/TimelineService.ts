import axios, { type AxiosResponse } from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';
import type { SearchResult } from '@/types/SearchResult/SearchResult';
import type { PageResponse } from '@/types/PageResponse';
import type { PostEntity } from '@/types/Posts/PostEntity';


export async function getTimeline(page: number): Promise<PageResponse<PostEntity>> {
    const response = await axios.get<PageResponse<PostEntity>>(
        `${SPRING_BOOT_LOCALHOST}/timeline/generate`,
        {
            withCredentials: true,
            params: {
                page,
                size: 5
            }
        }
    );

    return response.data;
}
export async function generateFollowSuggestions(userId: number):Promise<AxiosResponse<number[]>> {
  return await axios.get(
    `${SPRING_BOOT_LOCALHOST}/timeline/generateFollowSuggestions`,
    {
        withCredentials: true,
      params: { userId }
    }
  );
}

export async function getSearchResult( input: string ): Promise<AxiosResponse<SearchResult>> {
  return await axios.get(
    `${SPRING_BOOT_LOCALHOST}/timeline/getSearchResult`,
    {
        withCredentials: true,
      params: { input }
    }
  );
}