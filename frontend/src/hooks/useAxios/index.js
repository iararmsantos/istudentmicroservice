import React, { useEffect, useState } from 'react'
import axios from 'axios';

const useAxios = () => {
    const [response, setResponse] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    axios.interceptors.request.use((config) => {        
        return config;
    }, (error) => {
        return Promise.reject(error);
    });

    axios.interceptors.response.use((response) => {
        return response;
    }, (error) => {
        return Promise.reject(error);
    });

    let controller = new AbortController()

    useEffect(() => {
        return () => controller?.abort()
    }, [])

    const fetchData = async ({ url, method, data = { }, headers = { }, params = { } }) => {               
        setLoading(true);

        controller.abort();
        controller = new AbortController();
        try {
            const result = await axios({
                url,
                method,
                data,
                headers,
                params,
                signal: controller.signal
            })

            setResponse(result);
        } catch (error) {
            if(axios.isCancel(error)){
                console.error("Request cancelled ", error.message);
            } else {
                setError(error.response ?? error.message)
            }            
        } finally {
            setLoading(false)
        }
    }
console.log({response, error})
    return { response, error, loading, fetchData }
}

export default useAxios

//source: https://www.youtube.com/watch?v=WtijdoNfzYg