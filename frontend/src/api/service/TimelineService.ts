import axios, { type AxiosResponse } from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';
import type { SearchResult } from '@/types/SearchResult/SearchResult';


export async function getTimeline(userId: number): Promise<AxiosResponse<number[]>> {
  return await axios.get(
    `${SPRING_BOOT_LOCALHOST}/timeline/generate`,
    {
        withCredentials: true,
      params: { userId }
    }
  );
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