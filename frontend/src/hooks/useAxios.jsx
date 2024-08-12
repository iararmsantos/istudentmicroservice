import React, { useEffect, useState } from 'react';
import axios from 'axios';

const useAxios = ({url, method, body = null, headers = null}) => {
    const [response, setResponse] = useState(null);
    const[error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const fetchData = () => {
        setLoading(true);
        axios({
            method: method,
            url: url,
            headers: headers ? JSON.parse(headers) : {},
            data: body ? body : {},
        })
        .then((res) => {
            setResponse(res.data);
        })
        .catch((err) => {
            setError(err);
        })
        .finally(() => {
            setLoading(false);
        })
    };

    useEffect(() => {
        fetchData();
    }, [method, url])
  return { response, error, loading, fetchData}
}

export default useAxios